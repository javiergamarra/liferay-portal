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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Test
	public void testGetBlogPostingMyRating() throws Exception {
		BlogPosting postBlogPosting = testGetBlogPosting_addBlogPosting();

		Rating randomRating = randomRating();

		Rating postRating = testPostBlogPostingMyRating_addMyRating(
			postBlogPosting.getId(), randomRating);

		Rating getRating = blogPostingResource.getBlogPostingMyRating(
			postBlogPosting.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	@Test
	public void testPostBlogPostingMyRating() throws Exception {
		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting postBlogPosting = testPostSiteBlogPosting_addBlogPosting(
			randomBlogPosting);

		Rating randomRating = randomRating();

		Rating postRating = testPostBlogPostingMyRating_addMyRating(
			postBlogPosting.getId(), randomRating);

		Assert.assertTrue(equals(randomRating, postRating));
	}

	@Test
	public void testPutBlogPostingMyRating() throws Exception {
		BlogPosting postBlogPosting = testPutBlogPosting_addBlogPosting();

		testPostBlogPostingMyRating_addMyRating(
			postBlogPosting.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating = blogPostingResource.putBlogPostingMyRating(
			postBlogPosting.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);

		Rating getRating = blogPostingResource.getBlogPostingMyRating(
			postBlogPosting.getId());

		assertEquals(randomRating, getRating);
		assertValid(getRating);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "headline"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	protected Rating testPostBlogPostingMyRating_addMyRating(
			long blogPostingId, Rating rating)
		throws Exception {

		return blogPostingResource.postBlogPostingMyRating(
			blogPostingId, rating);
	}

}