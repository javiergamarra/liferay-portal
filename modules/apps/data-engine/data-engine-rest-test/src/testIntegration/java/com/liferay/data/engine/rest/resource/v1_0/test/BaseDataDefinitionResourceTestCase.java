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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataDefinitionResourceTestCase {

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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/data-engine/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testDeleteDataDefinition_addDataDefinition();

		assertResponseCode(
			200, invokeDeleteDataDefinitionResponse(dataDefinition.getId()));

		assertResponseCode(
			404, invokeGetDataDefinitionResponse(dataDefinition.getId()));
	}

	@Test
	public void testGetDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		DataDefinition getDataDefinition = invokeGetDataDefinition(
			postDataDefinition.getId());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	@Test
	public void testGetDataDefinitionsPage() throws Exception {
		Long groupId = testGetDataDefinitionsPage_getGroupId();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionsPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition2 =
			testGetDataDefinitionsPage_addDataDefinition(
				groupId, randomDataDefinition());

		Page<DataDefinition> page = invokeGetDataDefinitionsPage(
			groupId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDataDefinitionsPageWithPagination() throws Exception {
		Long groupId = testGetDataDefinitionsPage_getGroupId();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionsPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition2 =
			testGetDataDefinitionsPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition3 =
			testGetDataDefinitionsPage_addDataDefinition(
				groupId, randomDataDefinition());

		Page<DataDefinition> page1 = invokeGetDataDefinitionsPage(
			groupId, Pagination.of(2, 1));

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 = invokeGetDataDefinitionsPage(
			groupId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			new ArrayList<DataDefinition>() {
				{
					addAll(dataDefinitions1);
					addAll(dataDefinitions2);
				}
			});
	}

	@Test
	public void testGetDataDefinitionsSearchPage() throws Exception {
		Long groupId = testGetDataDefinitionsSearchPage_getGroupId();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionsSearchPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition2 =
			testGetDataDefinitionsSearchPage_addDataDefinition(
				groupId, randomDataDefinition());

		Page<DataDefinition> page = invokeGetDataDefinitionsSearchPage(
			groupId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDataDefinitionsSearchPageWithPagination()
		throws Exception {

		Long groupId = testGetDataDefinitionsSearchPage_getGroupId();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionsSearchPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition2 =
			testGetDataDefinitionsSearchPage_addDataDefinition(
				groupId, randomDataDefinition());
		DataDefinition dataDefinition3 =
			testGetDataDefinitionsSearchPage_addDataDefinition(
				groupId, randomDataDefinition());

		Page<DataDefinition> page1 = invokeGetDataDefinitionsSearchPage(
			groupId, Pagination.of(2, 1));

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 = invokeGetDataDefinitionsSearchPage(
			groupId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			new ArrayList<DataDefinition>() {
				{
					addAll(dataDefinitions1);
					addAll(dataDefinitions2);
				}
			});
	}

	@Test
	public void testPostDataDefinition() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostDataDefinition_addDataDefinition(randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition putDataDefinition = invokePutDataDefinition(
			postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition = invokeGetDataDefinition(
			putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected void assertEquals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		Assert.assertTrue(
			dataDefinition1 + " does not equal " + dataDefinition2,
			equals(dataDefinition1, dataDefinition2));
	}

	protected void assertEquals(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (int i = 0; i < dataDefinitions1.size(); i++) {
			DataDefinition dataDefinition1 = dataDefinitions1.get(i);
			DataDefinition dataDefinition2 = dataDefinitions2.get(i);

			assertEquals(dataDefinition1, dataDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (DataDefinition dataDefinition1 : dataDefinitions1) {
			boolean contains = false;

			for (DataDefinition dataDefinition2 : dataDefinitions2) {
				if (equals(dataDefinition1, dataDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataDefinitions2 + " does not contain " + dataDefinition1,
				contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(DataDefinition dataDefinition) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<DataDefinition> page) {
		boolean valid = false;

		Collection<DataDefinition> dataDefinitions = page.getItems();

		int size = dataDefinitions.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		if (dataDefinition1 == dataDefinition2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_dataDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataDefinitionResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		DataDefinition dataDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinitionFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(dataDefinition.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(dataDefinition.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("groupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("storageType")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getStorageType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteDataDefinition(Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteDataDefinitionResponse(
			Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected DataDefinition invokeGetDataDefinition(Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), DataDefinition.class);
	}

	protected Http.Response invokeGetDataDefinitionResponse(
			Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<DataDefinition> invokeGetDataDefinitionsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/data-definitions", groupId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<DataDefinition>>() {
			});
	}

	protected Http.Response invokeGetDataDefinitionsPageResponse(
			Long groupId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/data-definitions", groupId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<DataDefinition> invokeGetDataDefinitionsSearchPage(
			Long groupId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/data-definitions/search", groupId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<DataDefinition>>() {
			});
	}

	protected Http.Response invokeGetDataDefinitionsSearchPageResponse(
			Long groupId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/data-definitions/search", groupId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected DataDefinition invokePostDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/data-definitions", groupId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), DataDefinition.class);
	}

	protected Http.Response invokePostDataDefinitionResponse(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/data-definitions", groupId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected DataDefinition invokePutDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), DataDefinition.class);
	}

	protected Http.Response invokePutDataDefinitionResponse(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/data-definitions/{data-definition-id}",
					dataDefinitionId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected DataDefinition randomDataDefinition() {
		return new DataDefinition() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				groupId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				storageType = RandomTestUtil.randomString();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataDefinition testGetDataDefinitionsPage_addDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDataDefinitionsPage_getGroupId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataDefinition testGetDataDefinitionsSearchPage_addDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDataDefinitionsSearchPage_getGroupId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataDefinition testPostDataDefinition_addDataDefinition(
			DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	@Inject
	private DataDefinitionResource _dataDefinitionResource;

	private URL _resourceURL;

}