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

package com.liferay.headless.portal.instances.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code CompanyWebIdException} to a {@code 404} error.
 *
 * @author Alberto Chaparro
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Portal.Instances)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Portal.Instances.CompanyNoSuchCompanyExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class NoSuchCompanyExceptionMapper
	extends BaseExceptionMapper<NoSuchCompanyException> {

	@Override
	protected Problem getProblem(
		NoSuchCompanyException noSuchCompanyException) {

		return new Problem(
			Response.Status.NOT_FOUND,
			StringUtil.replaceFirst(
				noSuchCompanyException.getMessage(), "No Company",
				"No portal instance"));
	}

}