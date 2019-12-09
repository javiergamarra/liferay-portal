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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.client.dto.v1_0.DataModelPermission;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.resource.v1_0.DataModelPermissionResource;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataRecordCollectionTestUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataModelPermissionResourceTest
	extends BaseDataModelPermissionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDataModelPermissionResourceTestCase.setUpClass();

		User user = UserTestUtil.addUser(emailAddress, password);

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		passwordPolicy.setChangeRequired(false);
		passwordPolicy.setChangeable(false);

		PasswordPolicyLocalServiceUtil.updatePasswordPolicy(passwordPolicy);

		user.setPasswordReset(false);
		user.setPasswordModified(true);
		user.setAgreedToTermsOfUse(true);
		user.setReminderQueryQuestion("what-is-your-father's-middle-name");
		user.setReminderQueryAnswer(RandomTestUtil.randomString());

		UserLocalServiceUtil.updateUser(user);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddlRecordSet = DataRecordCollectionTestUtil.addRecordSet(
			DataDefinitionTestUtil.addDDMStructure(testGroup), testGroup,
			_resourceLocalService);
	}

	@Override
	public void testGetDataRecordCollectionDataModelPermissionsPage()
		throws Exception {

		Page<DataModelPermission> page = null;

		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataModelPermissionsPage_getDataRecordCollectionId();
		Long irrelevantDataRecordCollectionId =
			testGetDataRecordCollectionDataModelPermissionsPage_getIrrelevantDataRecordCollectionId();

		if (irrelevantDataRecordCollectionId != null) {
			DataModelPermission irrelevantDataModelPermission =
				testGetDataRecordCollectionDataModelPermissionsPage_addDataModelPermission(
					irrelevantDataRecordCollectionId,
					randomIrrelevantDataModelPermission());

			page =
				dataModelPermissionResource.
					getDataRecordCollectionDataModelPermissionsPage(
						irrelevantDataRecordCollectionId, null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataModelPermission),
				(List<DataModelPermission>)page.getItems());
			assertValid(page);
		}

		DataModelPermission dataModelPermission1 =
			testGetDataRecordCollectionDataModelPermissionsPage_addDataModelPermission(
				dataRecordCollectionId, randomDataModelPermission());

		DataModelPermission dataModelPermission2 =
			testGetDataRecordCollectionDataModelPermissionsPage_addDataModelPermission(
				dataRecordCollectionId, randomDataModelPermission());

		page =
			dataModelPermissionResource.
				getDataRecordCollectionDataModelPermissionsPage(
					dataRecordCollectionId, null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataModelPermission1, dataModelPermission2),
			(List<DataModelPermission>)page.getItems());
		assertValid(page);
	}

	@Override
	public void testPutDataRecordCollectionDataModelPermission()
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			dataModelPermissionResource.
				putDataRecordCollectionDataModelPermissionHttpResponse(
					_ddlRecordSet.getRecordSetId(),
					new DataModelPermission[] {randomDataModelPermission()});

		Assert.assertEquals(204, httpResponse.getStatusCode());

		System.out.println(emailAddress + "  " + password);

		dataModelPermissionResource = DataModelPermissionResource.builder(
		).locale(
			LocaleUtil.getDefault()
		).authentication(
			emailAddress, password
		).build();

		httpResponse =
			dataModelPermissionResource.
				putDataRecordCollectionDataModelPermissionHttpResponse(
					_ddlRecordSet.getRecordSetId(),
					new DataModelPermission[] {randomDataModelPermission()});

		Assert.assertEquals(403, httpResponse.getStatusCode());
	}

	@Override
	protected DataModelPermission randomDataModelPermission() throws Exception {
		return new DataModelPermission() {
			{
				actionIds = new String[] {ActionKeys.PERMISSIONS};
				roleName = RoleConstants.USER;
			}
		};
	}

	@Override
	protected DataModelPermission
			testGetDataRecordCollectionDataModelPermissionsPage_addDataModelPermission(
				Long dataRecordCollectionId,
				DataModelPermission dataModelPermission)
		throws Exception {

		dataModelPermissionResource.putDataRecordCollectionDataModelPermission(
			_ddlRecordSet.getRecordSetId(),
			new DataModelPermission[] {dataModelPermission});

		return dataModelPermission;
	}

	@Override
	protected Long
			testGetDataRecordCollectionDataModelPermissionsPage_getDataRecordCollectionId()
		throws Exception {

		return _ddlRecordSet.getRecordSetId();
	}

	private static final String emailAddress =
		RandomTestUtil.randomString() + "@liferay.com";
	private static final String password = RandomTestUtil.randomString();

	private DDLRecordSet _ddlRecordSet;

	@Inject
	private ResourceLocalService _resourceLocalService;

}