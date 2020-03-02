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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.Fragment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.FragmentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFragmentResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_fragmentResource.setContextCompany(testCompany);

		FragmentResource.Builder builder = FragmentResource.builder();

		fragmentResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Fragment fragment1 = randomFragment();

		String json = objectMapper.writeValueAsString(fragment1);

		Fragment fragment2 = FragmentSerDes.toDTO(json);

		Assert.assertTrue(equals(fragment1, fragment2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Fragment fragment = randomFragment();

		String json1 = objectMapper.writeValueAsString(fragment);
		String json2 = FragmentSerDes.toJSON(fragment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Fragment fragment = randomFragment();

		fragment.setContent(regex);
		fragment.setCss(regex);
		fragment.setDescription(regex);
		fragment.setHtml(regex);
		fragment.setJs(regex);
		fragment.setKey(regex);
		fragment.setName(regex);

		String json = FragmentSerDes.toJSON(fragment);

		Assert.assertFalse(json.contains(regex));

		fragment = FragmentSerDes.toDTO(json);

		Assert.assertEquals(regex, fragment.getContent());
		Assert.assertEquals(regex, fragment.getCss());
		Assert.assertEquals(regex, fragment.getDescription());
		Assert.assertEquals(regex, fragment.getHtml());
		Assert.assertEquals(regex, fragment.getJs());
		Assert.assertEquals(regex, fragment.getKey());
		Assert.assertEquals(regex, fragment.getName());
	}

	@Test
	public void testGetFragmentCollectionFragmentsPage() throws Exception {
		Page<Fragment> page =
			fragmentResource.getFragmentCollectionFragmentsPage(
				testGetFragmentCollectionFragmentsPage_getFragmentCollectionId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long fragmentCollectionId =
			testGetFragmentCollectionFragmentsPage_getFragmentCollectionId();
		Long irrelevantFragmentCollectionId =
			testGetFragmentCollectionFragmentsPage_getIrrelevantFragmentCollectionId();

		if ((irrelevantFragmentCollectionId != null)) {
			Fragment irrelevantFragment =
				testGetFragmentCollectionFragmentsPage_addFragment(
					irrelevantFragmentCollectionId, randomIrrelevantFragment());

			page = fragmentResource.getFragmentCollectionFragmentsPage(
				irrelevantFragmentCollectionId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFragment),
				(List<Fragment>)page.getItems());
			assertValid(page);
		}

		Fragment fragment1 = testGetFragmentCollectionFragmentsPage_addFragment(
			fragmentCollectionId, randomFragment());

		Fragment fragment2 = testGetFragmentCollectionFragmentsPage_addFragment(
			fragmentCollectionId, randomFragment());

		page = fragmentResource.getFragmentCollectionFragmentsPage(
			fragmentCollectionId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(fragment1, fragment2),
			(List<Fragment>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFragmentCollectionFragmentsPageWithPagination()
		throws Exception {

		Long fragmentCollectionId =
			testGetFragmentCollectionFragmentsPage_getFragmentCollectionId();

		Fragment fragment1 = testGetFragmentCollectionFragmentsPage_addFragment(
			fragmentCollectionId, randomFragment());

		Fragment fragment2 = testGetFragmentCollectionFragmentsPage_addFragment(
			fragmentCollectionId, randomFragment());

		Fragment fragment3 = testGetFragmentCollectionFragmentsPage_addFragment(
			fragmentCollectionId, randomFragment());

		Page<Fragment> page1 =
			fragmentResource.getFragmentCollectionFragmentsPage(
				fragmentCollectionId, Pagination.of(1, 2));

		List<Fragment> fragments1 = (List<Fragment>)page1.getItems();

		Assert.assertEquals(fragments1.toString(), 2, fragments1.size());

		Page<Fragment> page2 =
			fragmentResource.getFragmentCollectionFragmentsPage(
				fragmentCollectionId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Fragment> fragments2 = (List<Fragment>)page2.getItems();

		Assert.assertEquals(fragments2.toString(), 1, fragments2.size());

		Page<Fragment> page3 =
			fragmentResource.getFragmentCollectionFragmentsPage(
				fragmentCollectionId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(fragment1, fragment2, fragment3),
			(List<Fragment>)page3.getItems());
	}

	protected Fragment testGetFragmentCollectionFragmentsPage_addFragment(
			Long fragmentCollectionId, Fragment fragment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetFragmentCollectionFragmentsPage_getFragmentCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetFragmentCollectionFragmentsPage_getIrrelevantFragmentCollectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetFragment() throws Exception {
		Fragment postFragment = testGetFragment_addFragment();

		Fragment getFragment = fragmentResource.getFragment(
			postFragment.getId());

		assertEquals(postFragment, getFragment);
		assertValid(getFragment);
	}

	protected Fragment testGetFragment_addFragment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFragment() throws Exception {
		Fragment fragment = testGraphQLFragment_addFragment();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"fragment",
				new HashMap<String, Object>() {
					{
						put("fragmentId", fragment.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				fragment, dataJSONObject.getJSONObject("fragment")));
	}

	protected Fragment testGraphQLFragment_addFragment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Fragment fragment1, Fragment fragment2) {
		Assert.assertTrue(
			fragment1 + " does not equal " + fragment2,
			equals(fragment1, fragment2));
	}

	protected void assertEquals(
		List<Fragment> fragments1, List<Fragment> fragments2) {

		Assert.assertEquals(fragments1.size(), fragments2.size());

		for (int i = 0; i < fragments1.size(); i++) {
			Fragment fragment1 = fragments1.get(i);
			Fragment fragment2 = fragments2.get(i);

			assertEquals(fragment1, fragment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Fragment> fragments1, List<Fragment> fragments2) {

		Assert.assertEquals(fragments1.size(), fragments2.size());

		for (Fragment fragment1 : fragments1) {
			boolean contains = false;

			for (Fragment fragment2 : fragments2) {
				if (equals(fragment1, fragment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				fragments2 + " does not contain " + fragment1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Fragment> fragments, JSONArray jsonArray) {

		for (Fragment fragment : fragments) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(fragment, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + fragment, contains);
		}
	}

	protected void assertValid(Fragment fragment) {
		boolean valid = true;

		if (fragment.getDateCreated() == null) {
			valid = false;
		}

		if (fragment.getDateModified() == null) {
			valid = false;
		}

		if (fragment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (fragment.getConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (fragment.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (fragment.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("css", additionalAssertFieldName)) {
				if (fragment.getCss() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (fragment.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("html", additionalAssertFieldName)) {
				if (fragment.getHtml() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("js", additionalAssertFieldName)) {
				if (fragment.getJs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (fragment.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (fragment.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usageCount", additionalAssertFieldName)) {
				if (fragment.getUsageCount() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Fragment> page) {
		boolean valid = false;

		java.util.Collection<Fragment> fragments = page.getItems();

		int size = fragments.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Fragment fragment1, Fragment fragment2) {
		if (fragment1 == fragment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getConfiguration(),
						fragment2.getConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getContent(), fragment2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getCreator(), fragment2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("css", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getCss(), fragment2.getCss())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getDateCreated(),
						fragment2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getDateModified(),
						fragment2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getDescription(),
						fragment2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("html", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getHtml(), fragment2.getHtml())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(fragment1.getId(), fragment2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("js", additionalAssertFieldName)) {
				if (!Objects.deepEquals(fragment1.getJs(), fragment2.getJs())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getKey(), fragment2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getName(), fragment2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usageCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						fragment1.getUsageCount(), fragment2.getUsageCount())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equalsJSONObject(
		Fragment fragment, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("content", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getContent(),
						jsonObject.getString("content"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("css", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getCss(), jsonObject.getString("css"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("html", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getHtml(), jsonObject.getString("html"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("js", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getJs(), jsonObject.getString("js"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getKey(), jsonObject.getString("key"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usageCount", fieldName)) {
				if (!Objects.deepEquals(
						fragment.getUsageCount(),
						jsonObject.getInt("usageCount"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_fragmentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_fragmentResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Fragment fragment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("configuration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getContent()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("css")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getCss()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(fragment.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(fragment.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(fragment.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(fragment.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(fragment.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(fragment.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("html")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getHtml()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("js")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getJs()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(fragment.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("usageCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Fragment randomFragment() throws Exception {
		return new Fragment() {
			{
				content = RandomTestUtil.randomString();
				css = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				html = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				js = RandomTestUtil.randomString();
				key = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				usageCount = RandomTestUtil.randomInt();
			}
		};
	}

	protected Fragment randomIrrelevantFragment() throws Exception {
		Fragment randomIrrelevantFragment = randomFragment();

		return randomIrrelevantFragment;
	}

	protected Fragment randomPatchFragment() throws Exception {
		return randomFragment();
	}

	protected FragmentResource fragmentResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFragmentResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.delivery.resource.v1_0.FragmentResource
		_fragmentResource;

}