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

import com.liferay.headless.delivery.client.dto.v1_0.Layout;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.LayoutResource;
import com.liferay.headless.delivery.client.serdes.v1_0.LayoutSerDes;
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
public abstract class BaseLayoutResourceTestCase {

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

		_layoutResource.setContextCompany(testCompany);

		LayoutResource.Builder builder = LayoutResource.builder();

		layoutResource = builder.locale(
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

		Layout layout1 = randomLayout();

		String json = objectMapper.writeValueAsString(layout1);

		Layout layout2 = LayoutSerDes.toDTO(json);

		Assert.assertTrue(equals(layout1, layout2));
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

		Layout layout = randomLayout();

		String json1 = objectMapper.writeValueAsString(layout);
		String json2 = LayoutSerDes.toJSON(layout);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Layout layout = randomLayout();

		layout.setDescription(regex);
		layout.setFriendlyURL(regex);
		layout.setKeywords(regex);
		layout.setLayoutTemplateId(regex);
		layout.setName(regex);
		layout.setType(regex);

		String json = LayoutSerDes.toJSON(layout);

		Assert.assertFalse(json.contains(regex));

		layout = LayoutSerDes.toDTO(json);

		Assert.assertEquals(regex, layout.getDescription());
		Assert.assertEquals(regex, layout.getFriendlyURL());
		Assert.assertEquals(regex, layout.getKeywords());
		Assert.assertEquals(regex, layout.getLayoutTemplateId());
		Assert.assertEquals(regex, layout.getName());
		Assert.assertEquals(regex, layout.getType());
	}

	@Test
	public void testGetSiteLayoutsPage() throws Exception {
		Page<Layout> page = layoutResource.getSiteLayoutsPage(
			testGetSiteLayoutsPage_getSiteId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteLayoutsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteLayoutsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Layout irrelevantLayout = testGetSiteLayoutsPage_addLayout(
				irrelevantSiteId, randomIrrelevantLayout());

			page = layoutResource.getSiteLayoutsPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantLayout), (List<Layout>)page.getItems());
			assertValid(page);
		}

		Layout layout1 = testGetSiteLayoutsPage_addLayout(
			siteId, randomLayout());

		Layout layout2 = testGetSiteLayoutsPage_addLayout(
			siteId, randomLayout());

		page = layoutResource.getSiteLayoutsPage(siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(layout1, layout2), (List<Layout>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteLayoutsPageWithPagination() throws Exception {
		Long siteId = testGetSiteLayoutsPage_getSiteId();

		Layout layout1 = testGetSiteLayoutsPage_addLayout(
			siteId, randomLayout());

		Layout layout2 = testGetSiteLayoutsPage_addLayout(
			siteId, randomLayout());

		Layout layout3 = testGetSiteLayoutsPage_addLayout(
			siteId, randomLayout());

		Page<Layout> page1 = layoutResource.getSiteLayoutsPage(
			siteId, Pagination.of(1, 2));

		List<Layout> layouts1 = (List<Layout>)page1.getItems();

		Assert.assertEquals(layouts1.toString(), 2, layouts1.size());

		Page<Layout> page2 = layoutResource.getSiteLayoutsPage(
			siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Layout> layouts2 = (List<Layout>)page2.getItems();

		Assert.assertEquals(layouts2.toString(), 1, layouts2.size());

		Page<Layout> page3 = layoutResource.getSiteLayoutsPage(
			siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(layout1, layout2, layout3),
			(List<Layout>)page3.getItems());
	}

	protected Layout testGetSiteLayoutsPage_addLayout(
			Long siteId, Layout layout)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteLayoutsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteLayoutsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteLayoutsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"layouts",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject layoutsJSONObject = dataJSONObject.getJSONObject("layouts");

		Assert.assertEquals(0, layoutsJSONObject.get("totalCount"));

		Layout layout1 = testGraphQLLayout_addLayout();
		Layout layout2 = testGraphQLLayout_addLayout();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		layoutsJSONObject = dataJSONObject.getJSONObject("layouts");

		Assert.assertEquals(2, layoutsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(layout1, layout2),
			layoutsJSONObject.getJSONArray("items"));
	}

	protected Layout testGraphQLLayout_addLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Layout layout1, Layout layout2) {
		Assert.assertTrue(
			layout1 + " does not equal " + layout2, equals(layout1, layout2));
	}

	protected void assertEquals(List<Layout> layouts1, List<Layout> layouts2) {
		Assert.assertEquals(layouts1.size(), layouts2.size());

		for (int i = 0; i < layouts1.size(); i++) {
			Layout layout1 = layouts1.get(i);
			Layout layout2 = layouts2.get(i);

			assertEquals(layout1, layout2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Layout> layouts1, List<Layout> layouts2) {

		Assert.assertEquals(layouts1.size(), layouts2.size());

		for (Layout layout1 : layouts1) {
			boolean contains = false;

			for (Layout layout2 : layouts2) {
				if (equals(layout1, layout2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				layouts2 + " does not contain " + layout1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Layout> layouts, JSONArray jsonArray) {

		for (Layout layout : layouts) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(layout, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + layout, contains);
		}
	}

	protected void assertValid(Layout layout) {
		boolean valid = true;

		if (layout.getDateCreated() == null) {
			valid = false;
		}

		if (layout.getDateModified() == null) {
			valid = false;
		}

		if (layout.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(layout.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("columns", additionalAssertFieldName)) {
				if (layout.getColumns() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (layout.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (layout.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"fragmentEntryLinks", additionalAssertFieldName)) {

				if (layout.getFragmentEntryLinks() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyURL", additionalAssertFieldName)) {
				if (layout.getFriendlyURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (layout.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("layoutData", additionalAssertFieldName)) {
				if (layout.getLayoutData() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("layoutTemplateId", additionalAssertFieldName)) {
				if (layout.getLayoutTemplateId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("layouts", additionalAssertFieldName)) {
				if (layout.getLayouts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (layout.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (layout.getType() == null) {
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

	protected void assertValid(Page<Layout> page) {
		boolean valid = false;

		java.util.Collection<Layout> layouts = page.getItems();

		int size = layouts.size();

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

	protected boolean equals(Layout layout1, Layout layout2) {
		if (layout1 == layout2) {
			return true;
		}

		if (!Objects.equals(layout1.getSiteId(), layout2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("columns", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getColumns(), layout2.getColumns())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getCreator(), layout2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getDateCreated(), layout2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getDateModified(), layout2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getDescription(), layout2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"fragmentEntryLinks", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						layout1.getFragmentEntryLinks(),
						layout2.getFragmentEntryLinks())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getFriendlyURL(), layout2.getFriendlyURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(layout1.getId(), layout2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getKeywords(), layout2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("layoutData", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getLayoutData(), layout2.getLayoutData())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("layoutTemplateId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getLayoutTemplateId(),
						layout2.getLayoutTemplateId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("layouts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						layout1.getLayouts(), layout2.getLayouts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(layout1.getName(), layout2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(layout1.getType(), layout2.getType())) {
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

	protected boolean equalsJSONObject(Layout layout, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						layout.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyURL", fieldName)) {
				if (!Objects.deepEquals(
						layout.getFriendlyURL(),
						jsonObject.getString("friendlyURL"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						layout.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", fieldName)) {
				if (!Objects.deepEquals(
						layout.getKeywords(),
						jsonObject.getString("keywords"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("layoutTemplateId", fieldName)) {
				if (!Objects.deepEquals(
						layout.getLayoutTemplateId(),
						jsonObject.getString("layoutTemplateId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						layout.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", fieldName)) {
				if (!Objects.deepEquals(
						layout.getType(), jsonObject.getString("type"))) {

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

		if (!(_layoutResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_layoutResource;

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
		EntityField entityField, String operator, Layout layout) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("columns")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(layout.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(layout.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(layout.getDateCreated()));
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
						DateUtils.addSeconds(layout.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(layout.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(layout.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fragmentEntryLinks")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("friendlyURL")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getFriendlyURL()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getKeywords()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("layoutData")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("layoutTemplateId")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getLayoutTemplateId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("layouts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(layout.getType()));
			sb.append("'");

			return sb.toString();
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

	protected Layout randomLayout() throws Exception {
		return new Layout() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				friendlyURL = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				keywords = RandomTestUtil.randomString();
				layoutTemplateId = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				type = RandomTestUtil.randomString();
			}
		};
	}

	protected Layout randomIrrelevantLayout() throws Exception {
		Layout randomIrrelevantLayout = randomLayout();

		randomIrrelevantLayout.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantLayout;
	}

	protected Layout randomPatchLayout() throws Exception {
		return randomLayout();
	}

	protected LayoutResource layoutResource;
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
		BaseLayoutResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.LayoutResource
		_layoutResource;

}