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
import com.liferay.account.rest.client.dto.v1_0.Account;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.After;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Account testDeleteAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Account testGetAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		return _addAccount(account);
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Account testPatchAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		return _addAccount(account);
	}

	@Override
	protected Account testPutAccount_addAccount() throws Exception {
		return _addAccount();
	}

	private Account _addAccount() throws Exception {
		return _toAccount(_addAccountEntry());
	}

	private Account _addAccount(Account account) throws Exception {
		return _toAccount(_addAccountEntry(account));
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _addAccountEntry(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null);
	}

	private AccountEntry _addAccountEntry(Account account) throws Exception {
		return _addAccountEntry(
			account.getParentAccountId(), account.getName(),
			account.getDescription(), account.getDomains());
	}

	private AccountEntry _addAccountEntry(
			long parentAccountEntryId, String name, String description,
			String[] domains)
		throws Exception {

		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(), parentAccountEntryId, name,
			description, domains, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		return new Account() {
			{
				description = accountEntry.getDescription();
				domains = StringUtil.split(accountEntry.getDomains());
				id = accountEntry.getAccountEntryId();
				name = accountEntry.getName();
				parentAccountId = accountEntry.getParentAccountEntryId();
				status = accountEntry.getStatus();
			}
		};
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

}