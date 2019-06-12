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

package com.liferay.change.tracking.rest.internal.jaxrs.exception.mapper;

import com.liferay.change.tracking.engine.exception.CTCollectionDescriptionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTCollectionNameCTEngineException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CannotCreateCollectionException;
import com.liferay.portal.kernel.util.Validator;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Change.Tracking.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Change.Tracking.REST.CollectionCannotCreateCollectionExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class CollectionCannotCreateCollectionExceptionMapper
	implements ExceptionMapper<CannotCreateCollectionException> {

	@Override
	public Response toResponse(CannotCreateCollectionException ccce) {
		Throwable throwable = ccce.getCause();

		Response.Status status = Response.Status.fromStatusCode(460);

		if (throwable != null) {
			if (throwable instanceof CTCollectionDescriptionCTEngineException) {
				status = Response.Status.fromStatusCode(461);
			}
			else if (throwable instanceof CTCollectionNameCTEngineException) {
				if (Validator.isNull(throwable.getMessage())) {
					status = Response.Status.fromStatusCode(462);
				}
				else {
					status = Response.Status.fromStatusCode(463);
				}
			}
		}

		return Response.status(
			status
		).type(
			MediaType.TEXT_PLAIN
		).entity(
			ccce.getMessage()
		).build();
	}

}