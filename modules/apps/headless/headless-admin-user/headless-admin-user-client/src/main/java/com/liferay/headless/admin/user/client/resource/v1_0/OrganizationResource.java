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

package com.liferay.headless.admin.user.client.resource.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.serdes.v1_0.OrganizationSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface OrganizationResource {

	public static Builder builder() {
		return new Builder();
	}

	public void deleteOrganization(Long[] organizationIds) throws Exception;

	public HttpInvoker.HttpResponse deleteOrganizationHttpResponse(
			Long[] organizationIds)
		throws Exception;

	public Page<Organization> getOrganizationsPage(
			Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getOrganizationsPageHttpResponse(
			Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception;

	public Page<Long> patchOrganizationsPage(Organization[] organizations)
		throws Exception;

	public HttpInvoker.HttpResponse patchOrganizationsPageHttpResponse(
			Organization[] organizations)
		throws Exception;

	public Page<Organization> postOrganizationsPage(
			String comments, String country, String name,
			Long parentOrganizationId, String region, String type)
		throws Exception;

	public HttpInvoker.HttpResponse postOrganizationsPageHttpResponse(
			String comments, String country, String name,
			Long parentOrganizationId, String region, String type)
		throws Exception;

	public Page<Long> putOrganizationsPage(Organization[] organizations)
		throws Exception;

	public HttpInvoker.HttpResponse putOrganizationsPageHttpResponse(
			Organization[] organizations)
		throws Exception;

	public Organization getOrganization(Long organizationId) throws Exception;

	public HttpInvoker.HttpResponse getOrganizationHttpResponse(
			Long organizationId)
		throws Exception;

	public Page<Organization> getOrganizationOrganizationsPage(
			Long parentOrganizationId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getOrganizationOrganizationsPageHttpResponse(
				Long parentOrganizationId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public OrganizationResource build() {
			return new OrganizationResourceImpl(this);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder header(String key, String value) {
			_headers.put(key, value);

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		public Builder parameter(String key, String value) {
			_parameters.put(key, value);

			return this;
		}

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class OrganizationResourceImpl
		implements OrganizationResource {

		public void deleteOrganization(Long[] organizationIds)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteOrganizationHttpResponse(organizationIds);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteOrganizationHttpResponse(
				Long[] organizationIds)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			if (organizationIds != null) {
				for (int i = 0; i < organizationIds.length; i++) {
					httpInvoker.parameter(
						"organizationIds", String.valueOf(organizationIds[i]));
				}
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Organization> getOrganizationsPage(
				Boolean flatten, String search, String filterString,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrganizationsPageHttpResponse(
					flatten, search, filterString, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, OrganizationSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getOrganizationsPageHttpResponse(
				Boolean flatten, String search, String filterString,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (flatten != null) {
				httpInvoker.parameter("flatten", String.valueOf(flatten));
			}

			if (search != null) {
				httpInvoker.parameter("search", String.valueOf(search));
			}

			if (filterString != null) {
				httpInvoker.parameter("filter", filterString);
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			if (sortString != null) {
				httpInvoker.parameter("sort", sortString);
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Long> patchOrganizationsPage(Organization[] organizations)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				patchOrganizationsPageHttpResponse(organizations);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, OrganizationSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse patchOrganizationsPageHttpResponse(
				Organization[] organizations)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(organizations.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Organization> postOrganizationsPage(
				String comments, String country, String name,
				Long parentOrganizationId, String region, String type)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postOrganizationsPageHttpResponse(
					comments, country, name, parentOrganizationId, region,
					type);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, OrganizationSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse postOrganizationsPageHttpResponse(
				String comments, String country, String name,
				Long parentOrganizationId, String region, String type)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(type.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (comments != null) {
				httpInvoker.parameter("comments", String.valueOf(comments));
			}

			if (country != null) {
				httpInvoker.parameter("country", String.valueOf(country));
			}

			if (name != null) {
				httpInvoker.parameter("name", String.valueOf(name));
			}

			if (parentOrganizationId != null) {
				httpInvoker.parameter(
					"parentOrganizationId",
					String.valueOf(parentOrganizationId));
			}

			if (region != null) {
				httpInvoker.parameter("region", String.valueOf(region));
			}

			if (type != null) {
				httpInvoker.parameter("type", String.valueOf(type));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Long> putOrganizationsPage(Organization[] organizations)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putOrganizationsPageHttpResponse(organizations);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, OrganizationSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse putOrganizationsPageHttpResponse(
				Organization[] organizations)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(organizations.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Organization getOrganization(Long organizationId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getOrganizationHttpResponse(
				organizationId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return OrganizationSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getOrganizationHttpResponse(
				Long organizationId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations/{organizationId}",
				organizationId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<Organization> getOrganizationOrganizationsPage(
				Long parentOrganizationId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getOrganizationOrganizationsPageHttpResponse(
					parentOrganizationId, flatten, search, filterString,
					pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, OrganizationSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getOrganizationOrganizationsPageHttpResponse(
					Long parentOrganizationId, Boolean flatten, String search,
					String filterString, Pagination pagination,
					String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (flatten != null) {
				httpInvoker.parameter("flatten", String.valueOf(flatten));
			}

			if (search != null) {
				httpInvoker.parameter("search", String.valueOf(search));
			}

			if (filterString != null) {
				httpInvoker.parameter("filter", filterString);
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			if (sortString != null) {
				httpInvoker.parameter("sort", sortString);
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-admin-user/v1.0/organizations/{parentOrganizationId}/organizations",
				parentOrganizationId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private OrganizationResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			OrganizationResource.class.getName());

		private Builder _builder;

	}

}