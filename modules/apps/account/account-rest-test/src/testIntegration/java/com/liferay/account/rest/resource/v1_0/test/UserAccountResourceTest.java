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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.UserAccount;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountEntry = _getAccountEntry();
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	@Test
	public void testPostUserAccount() throws Exception {
		super.testPostUserAccount();

		UserAccount randomUserAccount = randomUserAccount();

		Assert.assertNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(),
				randomUserAccount.getExternalReferenceCode()));

		testPostUserAccount_addUserAccount(randomUserAccount);

		Assert.assertNotNull(
			_userLocalService.fetchUserByReferenceCode(
				TestPropsValues.getCompanyId(),
				randomUserAccount.getExternalReferenceCode()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"givenName", "familyName", "alternateName"};
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		return new UserAccount() {
			{
				additionalName = RandomTestUtil.randomString();
				alternateName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				externalReferenceCode = RandomTestUtil.randomString();
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				honorificPrefix = RandomTestUtil.randomString();
				honorificSuffix = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	@Override
	protected UserAccount
			testGetUserAccountsByExternalReferenceCodePage_addUserAccount(
				String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return userAccountResource.postUserAccountByExternalReferenceCode(
			externalReferenceCode, userAccount);
	}

	@Override
	protected String
		testGetUserAccountsByExternalReferenceCodePage_getExternalReferenceCode() {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected UserAccount testGetUserAccountsPage_addUserAccount(
			Long accountId, UserAccount accountUser)
		throws Exception {

		return _addUserAccount(accountId, accountUser);
	}

	@Override
	protected Long testGetUserAccountsPage_getAccountId() {
		return _getAccountEntryId();
	}

	protected UserAccount testGraphQLAccountUser_addUserAccount()
		throws Exception {

		return _addUserAccount(_getAccountEntryId(), randomUserAccount());
	}

	@Override
	protected UserAccount testPostUserAccount_addUserAccount(
			UserAccount accountUser)
		throws Exception {

		return _addUserAccount(_getAccountEntryId(), accountUser);
	}

	@Override
	protected UserAccount
			testPostUserAccountByExternalReferenceCode_addUserAccount(
				UserAccount accountUser)
		throws Exception {

		return userAccountResource.postUserAccountByExternalReferenceCode(
			_accountEntry.getExternalReferenceCode(), accountUser);
	}

	private UserAccount _addUserAccount(Long accountId, UserAccount accountUser)
		throws Exception {

		return userAccountResource.postUserAccount(accountId, accountUser);
	}

	private AccountEntry _getAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		accountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		_accountEntryLocalService.updateAccountEntry(accountEntry);

		return accountEntry;
	}

	private Long _getAccountEntryId() {
		return _accountEntry.getAccountEntryId();
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}