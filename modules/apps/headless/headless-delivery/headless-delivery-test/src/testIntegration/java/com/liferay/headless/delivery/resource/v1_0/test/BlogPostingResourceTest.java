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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.test.util.SearchTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Test
	public void testGraphQLGetMissingOptionalFieldReturnNoErrors()
		throws Exception {

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField("myRating", new GraphQLField("ratingValue")));

		BlogPosting blogPosting = testGraphQLBlogPosting_addBlogPosting();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"blogPosting",
				HashMapBuilder.put(
					"blogPostingId", (Object)blogPosting.getId()
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		Assert.assertTrue(jsonObject.isNull("errors"));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject blogPostingJSONObject = dataJSONObject.getJSONObject(
			"blogPosting");

		Assert.assertTrue(blogPostingJSONObject.isNull("myRating"));
	}

	@Test
	public void testGraphQLGetNonexistentBlogPostingReturnNotFoundError()
		throws Exception {

		List<GraphQLField> graphQLFields = getGraphQLFields();

		BlogPosting irrelevantBlogPosting = randomIrrelevantBlogPosting();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"blogPosting",
				HashMapBuilder.put(
					"blogPostingId", (Object)irrelevantBlogPosting.getId()
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

		Assert.assertEquals(1, errorsJSONArray.length());
		JSONObject errorJSONObject = errorsJSONArray.getJSONObject(0);

		JSONObject extensionsJSONObject = errorJSONObject.getJSONObject(
			"extensions");

		Assert.assertEquals(
			"Not Found", extensionsJSONObject.getString("code"));
	}

	@Override
	@Test
	public void testPutSiteBlogPostingSubscribe() throws Exception {
		BlogPosting blogPosting =
			testPutSiteBlogPostingSubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingSubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	@Override
	@Test
	public void testPutSiteBlogPostingUnsubscribe() throws Exception {
		BlogPosting blogPosting =
			testPutSiteBlogPostingUnsubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingUnsubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"articleBody", "description", "headline"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

}