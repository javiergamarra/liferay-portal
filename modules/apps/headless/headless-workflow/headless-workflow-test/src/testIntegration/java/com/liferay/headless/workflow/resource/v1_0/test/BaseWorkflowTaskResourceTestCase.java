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

package com.liferay.headless.workflow.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.workflow.dto.v1_0.ChangeDescription;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
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

import java.lang.reflect.InvocationTargetException;

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

import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BaseWorkflowTaskResourceTestCase {

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-workflow/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetRoleWorkflowTasksPage() throws Exception {
		Long roleId = testGetRoleWorkflowTasksPage_getRoleId();

		WorkflowTask workflowTask1 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());
		WorkflowTask workflowTask2 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		Page<WorkflowTask> page = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetRoleWorkflowTasksPageWithPagination() throws Exception {
		Long roleId = testGetRoleWorkflowTasksPage_getRoleId();

		WorkflowTask workflowTask1 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());
		WorkflowTask workflowTask2 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());
		WorkflowTask workflowTask3 =
			testGetRoleWorkflowTasksPage_addWorkflowTask(
				roleId, randomWorkflowTask());

		Page<WorkflowTask> page1 = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 = invokeGetRoleWorkflowTasksPage(
			roleId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			new ArrayList<WorkflowTask>() {
				{
					addAll(workflowTasks1);
					addAll(workflowTasks2);
				}
			});
	}

	protected WorkflowTask testGetRoleWorkflowTasksPage_addWorkflowTask(
			Long roleId, WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetRoleWorkflowTasksPage_getRoleId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<WorkflowTask> invokeGetRoleWorkflowTasksPage(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/roles/{role-id}/workflow-tasks", roleId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<WorkflowTask>>() {
			});
	}

	protected Http.Response invokeGetRoleWorkflowTasksPageResponse(
			Long roleId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/roles/{role-id}/workflow-tasks", roleId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetWorkflowTasksByWorkflowTask() throws Exception {
		String workflowTaskId =
			testGetWorkflowTasksByWorkflowTask_getWorkflowTaskId();

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
				workflowTaskId, randomWorkflowTask());
		WorkflowTask workflowTask2 =
			testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
				workflowTaskId, randomWorkflowTask());

		Page<WorkflowTask> page = invokeGetWorkflowTasksByWorkflowTask(
			workflowTaskId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTasksByWorkflowTaskWithPagination()
		throws Exception {

		String workflowTaskId =
			testGetWorkflowTasksByWorkflowTask_getWorkflowTaskId();

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
				workflowTaskId, randomWorkflowTask());
		WorkflowTask workflowTask2 =
			testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
				workflowTaskId, randomWorkflowTask());
		WorkflowTask workflowTask3 =
			testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
				workflowTaskId, randomWorkflowTask());

		Page<WorkflowTask> page1 = invokeGetWorkflowTasksByWorkflowTask(
			workflowTaskId, Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 = invokeGetWorkflowTasksByWorkflowTask(
			workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			new ArrayList<WorkflowTask>() {
				{
					addAll(workflowTasks1);
					addAll(workflowTasks2);
				}
			});
	}

	protected WorkflowTask testGetWorkflowTasksByWorkflowTask_addWorkflowTask(
			String workflowTaskId, WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetWorkflowTasksByWorkflowTask_getWorkflowTaskId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<WorkflowTask> invokeGetWorkflowTasksByWorkflowTask(
			String workflowTaskId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks-by/{workflow-task-id}", workflowTaskId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<WorkflowTask>>() {
			});
	}

	protected Http.Response invokeGetWorkflowTasksByWorkflowTaskResponse(
			String workflowTaskId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks-by/{workflow-task-id}", workflowTaskId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetWorkflowTask() throws Exception {
		WorkflowTask postWorkflowTask = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask getWorkflowTask = invokeGetWorkflowTask(
			postWorkflowTask.getId());

		assertEquals(postWorkflowTask, getWorkflowTask);
		assertValid(getWorkflowTask);
	}

	protected WorkflowTask testGetWorkflowTask_addWorkflowTask()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokeGetWorkflowTask(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/workflow-tasks/{workflow-task-id}", workflowTaskId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTask.class);
	}

	protected Http.Response invokeGetWorkflowTaskResponse(Long workflowTaskId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/workflow-tasks/{workflow-task-id}", workflowTaskId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostWorkflowTaskAssignToMe() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskAssignToMe_addWorkflowTask(randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskAssignToMe_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokePostWorkflowTaskAssignToMe(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-me",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTask.class);
	}

	protected Http.Response invokePostWorkflowTaskAssignToMeResponse(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-me",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostWorkflowTaskAssignToUser() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskAssignToUser_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskAssignToUser_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokePostWorkflowTaskAssignToUser(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-user",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTask.class);
	}

	protected Http.Response invokePostWorkflowTaskAssignToUserResponse(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/assign-to-user",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostWorkflowTaskChangeTransition() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskChangeTransition_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskChangeTransition_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokePostWorkflowTaskChangeTransition(
			Long workflowTaskId, ChangeDescription changeDescription)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/change-transition",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTask.class);
	}

	protected Http.Response invokePostWorkflowTaskChangeTransitionResponse(
			Long workflowTaskId, ChangeDescription changeDescription)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/change-transition",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostWorkflowTaskUpdateDueDate() throws Exception {
		WorkflowTask randomWorkflowTask = randomWorkflowTask();

		WorkflowTask postWorkflowTask =
			testPostWorkflowTaskUpdateDueDate_addWorkflowTask(
				randomWorkflowTask);

		assertEquals(randomWorkflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
	}

	protected WorkflowTask testPostWorkflowTaskUpdateDueDate_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowTask invokePostWorkflowTaskUpdateDueDate(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/update-due-date",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), WorkflowTask.class);
	}

	protected Http.Response invokePostWorkflowTaskUpdateDueDateResponse(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/workflow-tasks/{workflow-task-id}/update-due-date",
					workflowTaskId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		WorkflowTask workflowTask1, WorkflowTask workflowTask2) {

		Assert.assertTrue(
			workflowTask1 + " does not equal " + workflowTask2,
			equals(workflowTask1, workflowTask2));
	}

	protected void assertEquals(
		List<WorkflowTask> workflowTasks1, List<WorkflowTask> workflowTasks2) {

		Assert.assertEquals(workflowTasks1.size(), workflowTasks2.size());

		for (int i = 0; i < workflowTasks1.size(); i++) {
			WorkflowTask workflowTask1 = workflowTasks1.get(i);
			WorkflowTask workflowTask2 = workflowTasks2.get(i);

			assertEquals(workflowTask1, workflowTask2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowTask> workflowTasks1, List<WorkflowTask> workflowTasks2) {

		Assert.assertEquals(workflowTasks1.size(), workflowTasks2.size());

		for (WorkflowTask workflowTask1 : workflowTasks1) {
			boolean contains = false;

			for (WorkflowTask workflowTask2 : workflowTasks2) {
				if (equals(workflowTask1, workflowTask2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowTasks2 + " does not contain " + workflowTask1,
				contains);
		}
	}

	protected void assertValid(WorkflowTask workflowTask) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<WorkflowTask> page) {
		boolean valid = false;

		Collection<WorkflowTask> workflowTasks = page.getItems();

		int size = workflowTasks.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		WorkflowTask workflowTask1, WorkflowTask workflowTask2) {

		if (workflowTask1 == workflowTask2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_workflowTaskResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowTaskResource;

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
		EntityField entityField, String operator, WorkflowTask workflowTask) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("completed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompleted")) {
			sb.append(_dateFormat.format(workflowTask.getDateCompleted()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(workflowTask.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("definitionName")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDefinitionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dueDate")) {
			sb.append(_dateFormat.format(workflowTask.getDueDate()));

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("logs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("logsIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(workflowTask.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("objectReviewed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("transitions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected WorkflowTask randomWorkflowTask() {
		return new WorkflowTask() {
			{
				completed = RandomTestUtil.randomBoolean();
				dateCompleted = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				definitionName = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				dueDate = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected WorkflowTask randomPatchWorkflowTask() {
		return randomWorkflowTask();
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private WorkflowTaskResource _workflowTaskResource;

	private URL _resourceURL;

}