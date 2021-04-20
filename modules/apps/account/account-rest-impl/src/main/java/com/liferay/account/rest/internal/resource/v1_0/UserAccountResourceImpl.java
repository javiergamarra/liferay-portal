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

package com.liferay.account.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.rest.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.account.rest.internal.dto.v1_0.converter.AccountUserResourceDTOConverter;
import com.liferay.account.rest.internal.odata.entity.v1_0.AccountUserEntityModel;
import com.liferay.account.rest.resource.v1_0.UserAccountResource;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account.properties",
	scope = ServiceScope.PROTOTYPE, service = UserAccountResource.class
)
public class UserAccountResourceImpl extends BaseUserAccountResourceImpl {

	@Override
	public Page<UserAccount> getAccountUsersByExternalReferenceCodePage(
			String externalReferenceCode, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getAccountUsersPage(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			search, filter, pagination, sorts);
	}

	@Override
	public Page<UserAccount> getAccountUsersPage(
			Long accountId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"accountEntryIds", String.valueOf(accountId)),
					BooleanClauseOccur.MUST);
			},
			filter, User.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toAccountUser(
				_userLocalService.getUserById(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _accountUserEntityModel;
	}

	@Override
	public UserAccount postAccountUser(Long accountId, UserAccount userAccount)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountId, contextUser.getUserId(),
				userAccount.getAlternateName(), userAccount.getEmailAddress(),
				contextAcceptLanguage.getPreferredLocale(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount));

		User user = _userLocalService.getUser(
			accountEntryUserRel.getAccountUserId());

		if (userAccount.getExternalReferenceCode() != null) {
			user.setExternalReferenceCode(
				userAccount.getExternalReferenceCode());

			user = _userLocalService.updateUser(user);
		}

		return _toAccountUser(user);
	}

	@Override
	public UserAccount postAccountUserByExternalReferenceCode(
			String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return postAccountUser(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			userAccount);
	}

	private long _getListTypeId(String value, String type) {
		ListType listType = _listTypeLocalService.addListType(value, type);

		return listType.getListTypeId();
	}

	private long _getPrefixId(UserAccount accountUser) {
		return Optional.ofNullable(
			accountUser.getHonorificPrefix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_PREFIX)
		).orElse(
			0L
		);
	}

	private long _getSuffixId(UserAccount accountUser) {
		return Optional.ofNullable(
			accountUser.getHonorificSuffix()
		).map(
			prefix -> _getListTypeId(prefix, ListTypeConstants.CONTACT_SUFFIX)
		).orElse(
			0L
		);
	}

	private UserAccount _toAccountUser(User user) throws Exception {
		return _accountUserResourceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), _dtoConverterRegistry,
				contextHttpServletRequest, user.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			user);
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	private final AccountUserEntityModel _accountUserEntityModel =
		new AccountUserEntityModel();

	@Reference
	private AccountUserResourceDTOConverter _accountUserResourceDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}