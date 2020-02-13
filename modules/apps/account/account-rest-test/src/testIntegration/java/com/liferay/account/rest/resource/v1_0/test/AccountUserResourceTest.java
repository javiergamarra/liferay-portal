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
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountUserResourceTest extends BaseAccountUserResourceTestCase {

	@Override
	protected AccountUser testGetAccountUsersPage_addAccountUser(
			Long accountId, AccountUser accountUser)
		throws Exception {

		return _addAccountUser(accountId, accountUser);
	}

	@Override
	protected Long testGetAccountUsersPage_getAccountId() throws Exception {
		return _getAccountEntryId();
	}

	@Override
	protected Long testGetAccountUsersPage_getIrrelevantAccountId()
		throws Exception {

		return _getAccountEntryId();
	}

	@Override
	protected AccountUser testGraphQLAccountUser_addAccountUser()
		throws Exception {

		return _addAccountUser(_getAccountEntryId());
	}

	@Override
	protected AccountUser testPostAccountUser_addAccountUser(
			AccountUser accountUser)
		throws Exception {

		return _addAccountUser(_getAccountEntryId(), accountUser);
	}

	private AccountEntry _addAccountEntry() throws PortalException {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, WorkflowConstants.STATUS_APPROVED);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private AccountUser _addAccountUser(long accountId) throws Exception {
		return _toAccountUser(_addUser(accountId));
	}

	private AccountUser _addAccountUser(long accountId, AccountUser accountUser)
		throws Exception {

		return _toAccountUser(_addUser(accountId, accountUser));
	}

	private User _addUser(long accountId) throws Exception {
		return _addUser(accountId, randomAccountUser());
	}

	private User _addUser(long accountId, AccountUser accountUser)
		throws Exception {

		return _addUser(
			accountId, accountUser.getScreenName(),
			accountUser.getEmailAddress(), LocaleThreadLocal.getDefaultLocale(),
			accountUser.getFirstName(), accountUser.getMiddleName(),
			accountUser.getLastName(), _getPrefixId(accountUser),
			_getSuffixId(accountUser));
	}

	private User _addUser(
			long accountId, String screenName, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			long prefixId, long suffixId)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountId, TestPropsValues.getUserId(), screenName,
				emailAddress, locale, firstName, middleName, lastName, prefixId,
				suffixId);

		User user = _userLocalService.getUser(
			accountEntryUserRel.getAccountUserId());

		_users.add(user);

		return user;
	}

	private Long _getAccountEntryId() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		return accountEntry.getAccountEntryId();
	}

	private long _getListTypeId(String value, String type) {
		ListType listType = _listTypeLocalService.addListType(value, type);

		return listType.getListTypeId();
	}

	private long _getPrefixId(AccountUser accountUser) {
		return Optional.ofNullable(
			accountUser.getPrefix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_PREFIX)
		).orElse(
			0L
		);
	}

	private long _getSuffixId(AccountUser accountUser) {
		return Optional.ofNullable(
			accountUser.getSuffix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_SUFFIX)
		).orElse(
			0L
		);
	}

	private AccountUser _toAccountUser(User user) throws Exception {
		return new AccountUser() {
			{
				emailAddress = user.getEmailAddress();
				firstName = user.getFirstName();
				id = user.getUserId();
				lastName = user.getLastName();
				middleName = user.getMiddleName();

				Contact contact = user.getContact();

				long prefixId = contact.getPrefixId();

				if (prefixId > 0) {
					ListType prefixListType = _listTypeLocalService.getListType(
						prefixId);

					prefix = prefixListType.getName();
				}

				screenName = user.getScreenName();

				long suffixId = contact.getSuffixId();

				if (suffixId > 0) {
					ListType suffixListType = _listTypeLocalService.getListType(
						suffixId);

					suffix = suffixListType.getName();
				}
			}
		};
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}