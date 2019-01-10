/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.blog.apio.internal.architect.resource;

import com.liferay.apio.architect.annotation.Actions;
import com.liferay.apio.architect.annotation.Body;
import com.liferay.apio.architect.annotation.ParentId;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.portal.apio.exception.ValidationException;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting </a> resources through a web
 * API. The resources are mapped from the internal model {@code BlogsEntry}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ActionRouter.class)
public class BlogPostingActionRouter implements ActionRouter<BlogPosting> {

	@Actions.Create
	public BlogPosting addBlogsEntry(
		@ParentId(ContentSpace.class) long groupId,
		@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException {

		try {
			BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
				_getUserId(blogPosting, currentUser), blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyURLPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), blogPosting.getPublishedDate(),
				true, true, new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_getServiceContext(groupId, blogPosting));

			return new BlogPostingImpl(
				blogsEntry, _getBlogsEntryAssetTags(blogsEntry));
		}
		catch (DuplicateFriendlyURLEntryException dfurlee) {
			throw new ValidationException("Duplicate friendly URL", dfurlee);
		}
	}

	@Actions.Retrieve
	public PageItems<BlogPosting> getPageItems(
		Pagination pagination,
		@ParentId(ContentSpace.class) long contentSpaceId) {

		List<BlogsEntry> blogsEntries = _blogsEntryService.getGroupEntries(
			contentSpaceId, WorkflowConstants.STATUS_APPROVED,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _blogsEntryService.getGroupEntriesCount(
			contentSpaceId, WorkflowConstants.STATUS_APPROVED);

		Stream<BlogsEntry> stream = blogsEntries.stream();

		List<BlogPosting> blogPostings = stream.map(
			blogsEntry ->
				new BlogPostingImpl(
					blogsEntry, _getBlogsEntryAssetTags(blogsEntry))
		).collect(
			Collectors.toList()
		);

		return new PageItems<>(blogPostings, count);
	}

	@Actions.Replace
	public BlogPosting updateBlogsEntry(
			@ParentId(ContentSpace.class) long blogsEntryId,
			@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException {

		long userId = _getUserId(blogPosting, currentUser);
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogsEntryId);
		ImageSelector imageSelector = _getImageSelector(blogPosting);

		ServiceContext serviceContext = _getServiceContext(
			blogsEntry.getGroupId(), blogPosting);

		BlogsEntry updatedBlogsEntry = _blogsEntryLocalService.updateEntry(
			userId, blogsEntryId, blogPosting.getHeadline(),
			blogPosting.getAlternativeHeadline(),
			blogPosting.getFriendlyURLPath(), blogPosting.getDescription(),
			blogPosting.getArticleBody(), blogPosting.getPublishedDate(), true,
			true, new String[0], blogPosting.getCaption(), imageSelector, null,
			serviceContext);

		return new BlogPostingImpl(
			updatedBlogsEntry, _getBlogsEntryAssetTags(updatedBlogsEntry));
	}

	private List<String> _getBlogsEntryAssetTags(BlogsEntry blogsEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		return ListUtil.toList(assetTags, AssetTag::getName);
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		Long imageId = blogPosting.getImageId();

		if ((imageId == null) || imageId.equals(0L)) {
			return null;
		}

		return Try.fromFallible(
			() -> _dlAppLocalService.getFileEntry(imageId)
		).map(
			fileEntry -> new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0,\"width\": 0,\"x\": 0,\"y\": 0}")
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to find file entry with id " + imageId)
		);
	}

	private ServiceContext _getServiceContext(
		long groupId, BlogPosting blogPosting) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		List<Long> categories = blogPosting.getCategories();

		if (ListUtil.isNotEmpty(categories)) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toLongArray(categories));
		}

		List<String> keywords = blogPosting.getKeywords();

		if (ListUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(keywords));
		}

		Date createdDate = blogPosting.getCreatedDate();

		if (createdDate != null) {
			serviceContext.setCreateDate(createdDate);
		}

		Date modifiedDate = blogPosting.getModifiedDate();

		if (modifiedDate != null) {
			serviceContext.setModifiedDate(modifiedDate);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private long _getUserId(BlogPosting blogPosting, CurrentUser currentUser) {
		return Optional.ofNullable(
			blogPosting.getCreatorId()
		).filter(
			userId -> userId > 0
		).orElse(
			currentUser.getUserId()
		);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private static class BlogPostingImpl implements BlogPosting {

		private final BlogsEntry _blogsEntry;
		private final List<String> _keywords;

		public BlogPostingImpl(BlogsEntry blogsEntry, List<String> keywords) {
			_blogsEntry = blogsEntry;
			_keywords = keywords;
		}

		@Override
		public Long getId() {
			return _blogsEntry.getEntryId();
		}

		@Override
		public ClassNameClassPK getAggregateRatingId() {
			return ClassNameClassPK.create(_blogsEntry);
		}

		@Override
		public String getAlternativeHeadline() {
			return _blogsEntry.getSubtitle();
		}

		@Override
		public String getArticleBody() {
			return _blogsEntry.getContent();
		}

		@Override
		public String getCaption() {
			return _blogsEntry.getCoverImageCaption();
		}

		@Override
		public Long getContentSpaceId() {
			return _blogsEntry.getGroupId();
		}

		@Override
		public Date getCreatedDate() {
			return _blogsEntry.getCreateDate();
		}

		@Override
		public Long getCreatorId() {
			return _blogsEntry.getUserId();
		}

		@Override
		public String getDescription() {
			return _blogsEntry.getDescription();
		}

		@Override
		public String getEncodingFormat() {
			return "text/html";
		}

		@Override
		public String getFriendlyURLPath() {
			return _blogsEntry.getUrlTitle();
		}

		@Override
		public String getHeadline() {
			return _blogsEntry.getTitle();
		}

		@Override
		public Long getImageId() {
			long coverImageFileEntryId = _blogsEntry.getCoverImageFileEntryId();

			if (coverImageFileEntryId == 0L) {
				return null;
			}

			return coverImageFileEntryId;
		}

		@Override
		public List<String> getKeywords() {
			return _keywords;
		}

		@Override
		public Date getModifiedDate() {
			return _blogsEntry.getModifiedDate();
		}

		@Override
		public Date getPublishedDate() {
			return _blogsEntry.getDisplayDate();
		}
	}

}