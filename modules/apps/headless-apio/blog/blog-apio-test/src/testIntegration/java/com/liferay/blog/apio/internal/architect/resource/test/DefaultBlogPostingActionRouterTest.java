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

package com.liferay.blog.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.apio.exception.ValidationException;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class DefaultBlogPostingActionRouterTest
	extends BaseBlogPostingActionRouterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddBlogPosting() throws Exception {
		Date date = new Date();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		BlogPosting baseBlogPosting = new BlogPostingImpl(
			"alternativeHeadline", "articleBody", "caption",
			Collections.emptyList(), 0L, date, date, date, "description",
			"friendlyurlpath", "headline", 0L, Collections.emptyList());

		BlogPosting blogPosting = addBlogPosting(
			_group.getGroupId(), baseBlogPosting, user);

		Assert.assertEquals(
			baseBlogPosting.getArticleBody(), blogPosting.getArticleBody());
		Assert.assertEquals(
			baseBlogPosting.getCaption(), blogPosting.getCaption());
		Assert.assertEquals(date, blogPosting.getCreatedDate());
		Assert.assertEquals(
			baseBlogPosting.getDescription(), blogPosting.getDescription());
		Assert.assertEquals(date, blogPosting.getPublishedDate());
		Assert.assertEquals(date, blogPosting.getModifiedDate());
		Assert.assertEquals(
			baseBlogPosting.getAlternativeHeadline(),
			blogPosting.getAlternativeHeadline());
		Assert.assertEquals(baseBlogPosting.getHeadline(),
			blogPosting.getHeadline());
		Assert.assertEquals(
			baseBlogPosting.getFriendlyURLPath(), blogPosting.getFriendlyURLPath());
	}

	@Test
	public void testAddBlogPostingThrowsValidationException() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = _userLocalService.getUser(serviceContext.getUserId());

		String friendlyURLPath = "friendlyurlpath";

		BlogPosting blogPosting1 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(), 0L,
			new Date(), new Date(), new Date(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		addBlogPosting(_group.getGroupId(), blogPosting1, user);

		BlogPosting blogPosting2 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(), 0L,
			new Date(), new Date(), new Date(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		AbstractThrowableAssert abstractThrowableAssert =
			Assertions.assertThatThrownBy(
				() -> addBlogPosting(_group.getGroupId(), blogPosting2, user)
			).isInstanceOf(
				ValidationException.class
			);

		abstractThrowableAssert.hasMessage("Duplicate friendly URL");
	}

	@Test
	public void testAddBlogPostingWithSpecificUser() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User currentUser = _userLocalService.getUser(
			serviceContext.getUserId());

		User user = UserTestUtil.addUser();

		String friendlyURLPath = "friendlyurlpath";

		BlogPosting baseBlogPosting = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(),
			user.getUserId(), new Date(), new Date(), new Date(),
			RandomTestUtil.randomString(10), friendlyURLPath,
			RandomTestUtil.randomString(10), 0L, Collections.emptyList());

		BlogPosting blogPosting = addBlogPosting(
			_group.getGroupId(), baseBlogPosting, user);

		Assert.assertEquals(
			user.getUserId(), (long) blogPosting.getCreatorId());
		Assert.assertNotEquals(
			currentUser.getUserId(), (long) blogPosting.getCreatorId());
	}

	@Test
	public void testGetPageItems() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		PageItems<BlogPosting> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<BlogPosting> blogPostings =
			(List<BlogPosting>) pageItems.getItems();

		BlogPosting blogPosting = blogPostings.get(0);

		Assert.assertEquals(blogsEntry.getTitle(), blogPosting.getHeadline());
	}

	@Test
	public void testGetPageItemsWith1Pending() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		_blogsEntryLocalService.updateStatus(
			serviceContext.getUserId(), blogsEntry.getEntryId(),
			WorkflowConstants.STATUS_PENDING, serviceContext,
			Collections.emptyMap());

		PageItems<BlogPosting> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWith1Scheduled() throws Exception {
		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_addBlogEntry(displayDate, serviceContext);

		PageItems<BlogPosting> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(new Date(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<BlogPosting> pageItems = getPageItems(
				PaginationRequest.of(10, 1), _group.getGroupId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testReplaceBlogPosting() throws Exception {
		Date date = new Date();

		BlogPosting baseBlogPosting = new BlogPostingImpl(
			"alternativeHeadline", "articleBody", "caption",
			Collections.emptyList(), 0L, date, date, date, "description",
			"friendlyurlpath", "headline", 0L, Collections.emptyList());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		BlogPosting blogPosting1 = addBlogPosting(
			_group.getGroupId(), baseBlogPosting, user);

		String updatedHeadline = "updatedHeadline";

		BlogPosting updatedBlogPosting = new BlogPostingImpl(
			baseBlogPosting.getAlternativeHeadline(),
			baseBlogPosting.getArticleBody(), baseBlogPosting.getCaption(),
			Collections.emptyList(), 0L, date, date, date,
			baseBlogPosting.getDescription(),
			baseBlogPosting.getFriendlyURLPath(), updatedHeadline, 0L,
			Collections.emptyList());

		baseBlogPosting = replaceBlogPosting(
			blogPosting1.getId(), updatedBlogPosting, user);

		Assert.assertEquals(
			updatedBlogPosting.getArticleBody(),
			baseBlogPosting.getArticleBody());
		Assert.assertEquals(
			updatedBlogPosting.getCaption(), baseBlogPosting.getCaption());
		Assert.assertEquals(date, baseBlogPosting.getCreatedDate());
		Assert.assertEquals(
			updatedBlogPosting.getDescription(),
			baseBlogPosting.getDescription());
		Assert.assertEquals(date, baseBlogPosting.getModifiedDate());
		Assert.assertEquals(date, baseBlogPosting.getPublishedDate());
		Assert.assertEquals(
			updatedBlogPosting.getAlternativeHeadline(),
			baseBlogPosting.getAlternativeHeadline());
		Assert.assertEquals(
			updatedBlogPosting.getHeadline(),
			baseBlogPosting.getHeadline());
		Assert.assertEquals(
			updatedBlogPosting.getFriendlyURLPath(),
			baseBlogPosting.getFriendlyURLPath());
	}

	private BlogsEntry _addBlogEntry(
			Date displayDate, ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			serviceContext.getUserId(), "headline", "alternativeHeadline",
			"/friendly/url/path", "some description", "article body",
			displayDate, true, true, new String[0], "image caption", null, null,
			serviceContext);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

	private static class BlogPostingImpl implements BlogPosting {

		public BlogPostingImpl(
			String alternativeHeadline, String articleBody, String caption,
			List<Long> categories, long creatorId, Date createdDate,
			Date modifiedDate, Date publishedDate, String description,
			String friendlyURLPath, String headline, long imageId,
			List<String> keywords) {

			_alternativeHeadline = alternativeHeadline;
			_articleBody = articleBody;
			_caption = caption;
			_categories = categories;
			_creatorId = creatorId;
			_createdDate = createdDate;
			_modifiedDate = modifiedDate;
			_publishedDate = publishedDate;
			_description = description;
			_friendlyURLPath = friendlyURLPath;
			_headline = headline;
			_imageId = imageId;
			_keywords = keywords;
		}

		@Override
		public Long getId() {
			return null;
		}

		@Override
		public ClassNameClassPK getAggregateRatingId() {
			return null;
		}

		@Override
		public String getAlternativeHeadline() {
			return _alternativeHeadline;
		}

		@Override
		public Optional<String> getTest() {
			return Optional.empty();
		}

		@Override
		public String getArticleBody() {
			return _articleBody;
		}

		@Override
		public String getCaption() {
			return _caption;
		}

		@Override
		public List<Long> getCategories() {
			return _categories;
		}

		@Override
		public Long getContentSpaceId() {
			return null;
		}

		@Override
		public Date getCreatedDate() {
			return _createdDate;
		}

		@Override
		public Long getCreatorId() {
			return _creatorId;
		}

		@Override
		public String getDescription() {
			return _description;
		}

		@Override
		public String getEncodingFormat() {
			return null;
		}

		@Override
		public String getFriendlyURLPath() {
			return _friendlyURLPath;
		}

		@Override
		public String getHeadline() {
			return _headline;
		}

		@Override
		public Long getImageId() {
			return _imageId;
		}

		@Override
		public List<String> getKeywords() {
			return _keywords;
		}

		@Override
		public Date getModifiedDate() {
			return _modifiedDate;
		}

		@Override
		public Date getPublishedDate() {
			return _publishedDate;
		}

		private final String _alternativeHeadline;
		private final String _articleBody;
		private final String _caption;
		private final List<Long> _categories;
		private final Date _createdDate;
		private final long _creatorId;
		private final String _description;
		private final String _friendlyURLPath;
		private final String _headline;
		private final long _imageId;
		private final List<String> _keywords;
		private final Date _modifiedDate;
		private final Date _publishedDate;

	}

}