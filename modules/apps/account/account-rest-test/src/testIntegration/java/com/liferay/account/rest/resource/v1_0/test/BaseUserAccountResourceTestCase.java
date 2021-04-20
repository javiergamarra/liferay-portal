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

package com.liferay.account.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.account.rest.client.dto.v1_0.UserAccount;
import com.liferay.account.rest.client.http.HttpInvoker;
import com.liferay.account.rest.client.pagination.Page;
import com.liferay.account.rest.client.pagination.Pagination;
import com.liferay.account.rest.client.resource.v1_0.UserAccountResource;
import com.liferay.account.rest.client.serdes.v1_0.UserAccountSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
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
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceTestCase {

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

		_userAccountResource.setContextCompany(testCompany);

		UserAccountResource.Builder builder = UserAccountResource.builder();

		userAccountResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		UserAccount userAccount1 = randomUserAccount();

		String json = objectMapper.writeValueAsString(userAccount1);

		UserAccount userAccount2 = UserAccountSerDes.toDTO(json);

		Assert.assertTrue(equals(userAccount1, userAccount2));
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

		UserAccount userAccount = randomUserAccount();

		String json1 = objectMapper.writeValueAsString(userAccount);
		String json2 = UserAccountSerDes.toJSON(userAccount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		UserAccount userAccount = randomUserAccount();

		String json = UserAccountSerDes.toJSON(userAccount);

		Assert.assertFalse(json.contains(regex));

		userAccount = UserAccountSerDes.toDTO(json);
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePage()
		throws Exception {

		Page<UserAccount> page =
			userAccountResource.getAccountUsersByExternalReferenceCodePage(
				testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getIrrelevantExternalReferenceCode();

		if (irrelevantExternalReferenceCode != null) {
			UserAccount irrelevantUserAccount =
				testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
					irrelevantExternalReferenceCode,
					randomIrrelevantUserAccount());

			page =
				userAccountResource.getAccountUsersByExternalReferenceCodePage(
					irrelevantExternalReferenceCode, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantUserAccount),
				(List<UserAccount>)page.getItems());
			assertValid(page);
		}

		UserAccount userAccount1 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		UserAccount userAccount2 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		page = userAccountResource.getAccountUsersByExternalReferenceCodePage(
			externalReferenceCode, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode();

		UserAccount userAccount1 = randomUserAccount();

		userAccount1 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, userAccount1);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page =
				userAccountResource.getAccountUsersByExternalReferenceCodePage(
					externalReferenceCode, null,
					getFilterString(entityField, "between", userAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode();

		UserAccount userAccount1 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserAccount userAccount2 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page =
				userAccountResource.getAccountUsersByExternalReferenceCodePage(
					externalReferenceCode, null,
					getFilterString(entityField, "eq", userAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode();

		UserAccount userAccount1 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		UserAccount userAccount2 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		UserAccount userAccount3 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		Page<UserAccount> page1 =
			userAccountResource.getAccountUsersByExternalReferenceCodePage(
				externalReferenceCode, null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 =
			userAccountResource.getAccountUsersByExternalReferenceCodePage(
				externalReferenceCode, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<UserAccount> userAccounts2 = (List<UserAccount>)page2.getItems();

		Assert.assertEquals(userAccounts2.toString(), 1, userAccounts2.size());

		Page<UserAccount> page3 =
			userAccountResource.getAccountUsersByExternalReferenceCodePage(
				externalReferenceCode, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			(List<UserAccount>)page3.getItems());
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithSortDateTime()
		throws Exception {

		testGetAccountUsersByExternalReferenceCodePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, userAccount1, userAccount2) -> {
				BeanUtils.setProperty(
					userAccount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithSortInteger()
		throws Exception {

		testGetAccountUsersByExternalReferenceCodePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, userAccount1, userAccount2) -> {
				BeanUtils.setProperty(userAccount1, entityField.getName(), 0);
				BeanUtils.setProperty(userAccount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountUsersByExternalReferenceCodePageWithSortString()
		throws Exception {

		testGetAccountUsersByExternalReferenceCodePageWithSort(
			EntityField.Type.STRING,
			(entityField, userAccount1, userAccount2) -> {
				Class<?> clazz = userAccount1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountUsersByExternalReferenceCodePageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, UserAccount, UserAccount, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, userAccount1, userAccount2);
		}

		userAccount1 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, userAccount1);

		userAccount2 =
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				externalReferenceCode, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage =
				userAccountResource.getAccountUsersByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage =
				userAccountResource.getAccountUsersByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	protected UserAccount
			testGetAccountUsersByExternalReferenceCodePage_addUserAccount(
				String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountUsersByExternalReferenceCodePage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountUsersByExternalReferenceCodePage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountUserByExternalReferenceCode() throws Exception {
		UserAccount randomUserAccount = randomUserAccount();

		UserAccount postUserAccount =
			testPostAccountUserByExternalReferenceCode_addUserAccount(
				randomUserAccount);

		assertEquals(randomUserAccount, postUserAccount);
		assertValid(postUserAccount);
	}

	protected UserAccount
			testPostAccountUserByExternalReferenceCode_addUserAccount(
				UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountUsersPage() throws Exception {
		Page<UserAccount> page = userAccountResource.getAccountUsersPage(
			testGetAccountUsersPage_getAccountId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long accountId = testGetAccountUsersPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountUsersPage_getIrrelevantAccountId();

		if (irrelevantAccountId != null) {
			UserAccount irrelevantUserAccount =
				testGetAccountUsersPage_addUserAccount(
					irrelevantAccountId, randomIrrelevantUserAccount());

			page = userAccountResource.getAccountUsersPage(
				irrelevantAccountId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantUserAccount),
				(List<UserAccount>)page.getItems());
			assertValid(page);
		}

		UserAccount userAccount1 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		UserAccount userAccount2 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		page = userAccountResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountUsersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		UserAccount userAccount1 = randomUserAccount();

		userAccount1 = testGetAccountUsersPage_addUserAccount(
			accountId, userAccount1);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = userAccountResource.getAccountUsersPage(
				accountId, null,
				getFilterString(entityField, "between", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		UserAccount userAccount1 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserAccount userAccount2 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = userAccountResource.getAccountUsersPage(
				accountId, null,
				getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersPageWithPagination() throws Exception {
		Long accountId = testGetAccountUsersPage_getAccountId();

		UserAccount userAccount1 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		UserAccount userAccount2 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		UserAccount userAccount3 = testGetAccountUsersPage_addUserAccount(
			accountId, randomUserAccount());

		Page<UserAccount> page1 = userAccountResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 = userAccountResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<UserAccount> userAccounts2 = (List<UserAccount>)page2.getItems();

		Assert.assertEquals(userAccounts2.toString(), 1, userAccounts2.size());

		Page<UserAccount> page3 = userAccountResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			(List<UserAccount>)page3.getItems());
	}

	@Test
	public void testGetAccountUsersPageWithSortDateTime() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, userAccount1, userAccount2) -> {
				BeanUtils.setProperty(
					userAccount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountUsersPageWithSortInteger() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, userAccount1, userAccount2) -> {
				BeanUtils.setProperty(userAccount1, entityField.getName(), 0);
				BeanUtils.setProperty(userAccount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountUsersPageWithSortString() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.STRING,
			(entityField, userAccount1, userAccount2) -> {
				Class<?> clazz = userAccount1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						userAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						userAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountUsersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, UserAccount, UserAccount, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, userAccount1, userAccount2);
		}

		userAccount1 = testGetAccountUsersPage_addUserAccount(
			accountId, userAccount1);

		userAccount2 = testGetAccountUsersPage_addUserAccount(
			accountId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = userAccountResource.getAccountUsersPage(
				accountId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage =
				userAccountResource.getAccountUsersPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	protected UserAccount testGetAccountUsersPage_addUserAccount(
			Long accountId, UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountUsersPage_getAccountId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountUsersPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountUser() throws Exception {
		UserAccount randomUserAccount = randomUserAccount();

		UserAccount postUserAccount = testPostAccountUser_addUserAccount(
			randomUserAccount);

		assertEquals(randomUserAccount, postUserAccount);
		assertValid(postUserAccount);
	}

	protected UserAccount testPostAccountUser_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		UserAccount userAccount1, UserAccount userAccount2) {

		Assert.assertTrue(
			userAccount1 + " does not equal " + userAccount2,
			equals(userAccount1, userAccount2));
	}

	protected void assertEquals(
		List<UserAccount> userAccounts1, List<UserAccount> userAccounts2) {

		Assert.assertEquals(userAccounts1.size(), userAccounts2.size());

		for (int i = 0; i < userAccounts1.size(); i++) {
			UserAccount userAccount1 = userAccounts1.get(i);
			UserAccount userAccount2 = userAccounts2.get(i);

			assertEquals(userAccount1, userAccount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<UserAccount> userAccounts1, List<UserAccount> userAccounts2) {

		Assert.assertEquals(userAccounts1.size(), userAccounts2.size());

		for (UserAccount userAccount1 : userAccounts1) {
			boolean contains = false;

			for (UserAccount userAccount2 : userAccounts2) {
				if (equals(userAccount1, userAccount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				userAccounts2 + " does not contain " + userAccount1, contains);
		}
	}

	protected void assertValid(UserAccount userAccount) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<UserAccount> page) {
		boolean valid = false;

		java.util.Collection<UserAccount> userAccounts = page.getItems();

		int size = userAccounts.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.admin.user.dto.v1_0.UserAccount.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		UserAccount userAccount1, UserAccount userAccount2) {

		if (userAccount1 == userAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_userAccountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_userAccountResource;

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
		EntityField entityField, String operator, UserAccount userAccount) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected UserAccount randomUserAccount() throws Exception {
		return new UserAccount() {
			{
			}
		};
	}

	protected UserAccount randomIrrelevantUserAccount() throws Exception {
		UserAccount randomIrrelevantUserAccount = randomUserAccount();

		return randomIrrelevantUserAccount;
	}

	protected UserAccount randomPatchUserAccount() throws Exception {
		return randomUserAccount();
	}

	protected UserAccountResource userAccountResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUserAccountResourceTestCase.class);

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
	private com.liferay.account.rest.resource.v1_0.UserAccountResource
		_userAccountResource;

}