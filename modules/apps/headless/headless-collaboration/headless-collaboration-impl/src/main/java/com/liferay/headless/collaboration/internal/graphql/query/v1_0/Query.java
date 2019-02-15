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

package com.liferay.headless.collaboration.internal.graphql.query.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.dto.v1_0.Creator;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.collaboration.resource.v1_0.CreatorResource;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting getBlogPosting( @GraphQLName("blog-posting-id") Long blogPostingId ) throws Exception {

		return _getBlogPostingResource().getBlogPosting( blogPostingId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPosting> getContentSpaceBlogPostingsPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getBlogPostingResource().getContentSpaceBlogPostingsPage( contentSpaceId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImage> getContentSpaceBlogPostingImagesPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getBlogPostingImageResource().getContentSpaceBlogPostingImagesPage( contentSpaceId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage getImageObject( @GraphQLName("image-object-id") Long imageObjectId ) throws Exception {

		return _getBlogPostingImageResource().getImageObject( imageObjectId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getCommentResource().getBlogPostingCommentsPage( blogPostingId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment getComment( @GraphQLName("comment-id") Long commentId ) throws Exception {

		return _getCommentResource().getComment( commentId );

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage( @GraphQLName("comment-id") Long commentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {

		return _getCommentResource().getCommentCommentsPage( commentId , Pagination.of(perPage, page) ).getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Creator getCreator( @GraphQLName("creator-id") Long creatorId ) throws Exception {

		return _getCreatorResource().getCreator( creatorId );

	}

	private static BlogPostingResource _getBlogPostingResource() {
			return _blogPostingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingResource, BlogPostingResource> _blogPostingResourceServiceTracker;

	private static BlogPostingImageResource _getBlogPostingImageResource() {
			return _blogPostingImageResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingImageResource, BlogPostingImageResource> _blogPostingImageResourceServiceTracker;

	private static CommentResource _getCommentResource() {
			return _commentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CommentResource, CommentResource> _commentResourceServiceTracker;

	private static CreatorResource _getCreatorResource() {
			return _creatorResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CreatorResource, CreatorResource> _creatorResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<BlogPostingResource, BlogPostingResource> blogPostingResourceServiceTracker =
			new ServiceTracker<BlogPostingResource, BlogPostingResource>(bundle.getBundleContext(), BlogPostingResource.class, null);

		blogPostingResourceServiceTracker.open();

		_blogPostingResourceServiceTracker = blogPostingResourceServiceTracker;

		ServiceTracker<BlogPostingImageResource, BlogPostingImageResource> blogPostingImageResourceServiceTracker =
			new ServiceTracker<BlogPostingImageResource, BlogPostingImageResource>(bundle.getBundleContext(), BlogPostingImageResource.class, null);

		blogPostingImageResourceServiceTracker.open();

		_blogPostingImageResourceServiceTracker = blogPostingImageResourceServiceTracker;

		ServiceTracker<CommentResource, CommentResource> commentResourceServiceTracker =
			new ServiceTracker<CommentResource, CommentResource>(bundle.getBundleContext(), CommentResource.class, null);

		commentResourceServiceTracker.open();

		_commentResourceServiceTracker = commentResourceServiceTracker;

		ServiceTracker<CreatorResource, CreatorResource> creatorResourceServiceTracker =
			new ServiceTracker<CreatorResource, CreatorResource>(bundle.getBundleContext(), CreatorResource.class, null);

		creatorResourceServiceTracker.open();

		_creatorResourceServiceTracker = creatorResourceServiceTracker;

	}

}