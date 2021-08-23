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

package com.liferay.headless.admin.content.internal.resource.v1_0;

import com.liferay.headless.admin.content.resource.v1_0.ContentStructureResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseContentStructureResourceImpl
	implements ContentStructureResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-content/v1.0/asset-libraries/{assetLibraryId}/content-structures'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/content-structures")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public Page<com.liferay.headless.delivery.dto.v1_0.ContentStructure>
			getAssetLibraryContentStructuresPage(
				@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId")
					Long assetLibraryId,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context com.liferay.portal.vulcan.aggregation.Aggregation
					aggregation,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-content/v1.0/asset-libraries/{assetLibraryId}/content-structures'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/asset-libraries/{assetLibraryId}/content-structures")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public com.liferay.headless.delivery.dto.v1_0.ContentStructure
			postAssetLibraryContentStructure(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				com.liferay.headless.delivery.dto.v1_0.ContentStructure
					contentStructure)
		throws Exception {

		return new com.liferay.headless.delivery.dto.v1_0.ContentStructure();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-content/v1.0/content-structures/{contentStructureId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "contentStructureId")}
	)
	@Path("/content-structures/{contentStructureId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public void deleteContentStructure(
			@NotNull @Parameter(hidden = true) @PathParam("contentStructureId")
				Long contentStructureId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-content/v1.0/content-structures/{contentStructureId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the content structure.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "contentStructureId")}
	)
	@Path("/content-structures/{contentStructureId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public com.liferay.headless.delivery.dto.v1_0.ContentStructure
			getContentStructure(
				@NotNull @Parameter(hidden = true)
				@PathParam("contentStructureId")
				Long contentStructureId)
		throws Exception {

		return new com.liferay.headless.delivery.dto.v1_0.ContentStructure();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-content/v1.0/content-structures/{contentStructureId}'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "contentStructureId")}
	)
	@PATCH
	@Path("/content-structures/{contentStructureId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public com.liferay.headless.delivery.dto.v1_0.ContentStructure
			patchContentStructure(
				@NotNull @Parameter(hidden = true)
				@PathParam("contentStructureId")
				Long contentStructureId,
				com.liferay.headless.delivery.dto.v1_0.ContentStructure
					contentStructure)
		throws Exception {

		com.liferay.headless.delivery.dto.v1_0.ContentStructure
			existingContentStructure = getContentStructure(contentStructureId);

		if (contentStructure.getAssetLibraryKey() != null) {
			existingContentStructure.setAssetLibraryKey(
				contentStructure.getAssetLibraryKey());
		}

		if (contentStructure.getAvailableLanguages() != null) {
			existingContentStructure.setAvailableLanguages(
				contentStructure.getAvailableLanguages());
		}

		if (contentStructure.getDateCreated() != null) {
			existingContentStructure.setDateCreated(
				contentStructure.getDateCreated());
		}

		if (contentStructure.getDateModified() != null) {
			existingContentStructure.setDateModified(
				contentStructure.getDateModified());
		}

		if (contentStructure.getDescription() != null) {
			existingContentStructure.setDescription(
				contentStructure.getDescription());
		}

		if (contentStructure.getDescription_i18n() != null) {
			existingContentStructure.setDescription_i18n(
				contentStructure.getDescription_i18n());
		}

		if (contentStructure.getName() != null) {
			existingContentStructure.setName(contentStructure.getName());
		}

		if (contentStructure.getName_i18n() != null) {
			existingContentStructure.setName_i18n(
				contentStructure.getName_i18n());
		}

		if (contentStructure.getSiteId() != null) {
			existingContentStructure.setSiteId(contentStructure.getSiteId());
		}

		preparePatch(contentStructure, existingContentStructure);

		return putContentStructure(
			contentStructureId, existingContentStructure);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-content/v1.0/content-structures/{contentStructureId}'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "contentStructureId")}
	)
	@Path("/content-structures/{contentStructureId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "ContentStructure")})
	public com.liferay.headless.delivery.dto.v1_0.ContentStructure
			putContentStructure(
				@NotNull @Parameter(hidden = true)
				@PathParam("contentStructureId")
				Long contentStructureId,
				com.liferay.headless.delivery.dto.v1_0.ContentStructure
					contentStructure)
		throws Exception {

		return new com.liferay.headless.delivery.dto.v1_0.ContentStructure();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-content/v1.0/sites/{siteId}/content-structures'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's content structures. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/content-structures")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public Page<com.liferay.headless.delivery.dto.v1_0.ContentStructure>
			getSiteContentStructuresPage(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context com.liferay.portal.vulcan.aggregation.Aggregation
					aggregation,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-content/v1.0/sites/{siteId}/content-structures'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/content-structures")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "ContentStructure")})
	public com.liferay.headless.delivery.dto.v1_0.ContentStructure
			postSiteContentStructure(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				com.liferay.headless.delivery.dto.v1_0.ContentStructure
					contentStructure)
		throws Exception {

		return new com.liferay.headless.delivery.dto.v1_0.ContentStructure();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(
		com.liferay.headless.delivery.dto.v1_0.ContentStructure
			contentStructure,
		com.liferay.headless.delivery.dto.v1_0.ContentStructure
			existingContentStructure) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;

}