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

package com.liferay.account.rest.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = AccountUserResourceDTOConverter.class)
public class AccountUserResourceDTOConverter
	implements DTOConverter<User, UserAccount> {

	@Override
	public String getContentType() {
		return _getUserDTOConverter().getContentType();
	}

	@Override
	public User getObject(String externalReferenceCode) throws Exception {
		return _getUserDTOConverter().getObject(externalReferenceCode);
	}

	public long getUserId(String externalReferenceCode) throws Exception {
		User user = getObject(externalReferenceCode);

		return user.getUserId();
	}

	@Override
	public UserAccount toDTO(DTOConverterContext dtoConverterContext, User user)
		throws Exception {

		return _getUserDTOConverter().toDTO(dtoConverterContext, user);
	}

	@SuppressWarnings("unchecked")
	private DTOConverter<User, UserAccount> _getUserDTOConverter() {
		return (DTOConverter<User, UserAccount>)
			_dtoConverterRegistry.getDTOConverter(User.class.getName());
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}