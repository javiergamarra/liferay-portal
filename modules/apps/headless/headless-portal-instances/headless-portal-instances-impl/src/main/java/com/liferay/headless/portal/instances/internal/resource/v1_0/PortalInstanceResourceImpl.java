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

package com.liferay.headless.portal.instances.internal.resource.v1_0;

import com.liferay.headless.portal.instances.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.resource.v1_0.PortalInstanceResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alberto Chaparro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/portal-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = PortalInstanceResource.class
)
public class PortalInstanceResourceImpl extends BasePortalInstanceResourceImpl {

	@Override
	public void deletePortalInstance(String webId) throws Exception {
		Company company = _companyLocalService.getCompanyByWebId(webId);

		if (company.isActive()) {
			throw new ValidationException(
				"Deactivate the portal instance before deleting it");
		}

		if (_portalInstancesLocalService.getDefaultCompanyId() ==
				company.getCompanyId()) {

			throw new ValidationException(
				"Select other default portal instance before deleting it");
		}

		_companyLocalService.deleteCompany(company.getCompanyId());
	}

	@Override
	public PortalInstance getPortalInstance(String webId) throws Exception {
		return _getPortalInstance(
			_companyLocalService.getCompanyByWebId(webId));
	}

	@Override
	public Page<PortalInstance> getPortalInstancesPage(String skipDefault)
		throws Exception {

		List<Company> companies = _companyLocalService.getCompanies(false);

		boolean skipDefaultCompany = GetterUtil.get(skipDefault, false);

		List<PortalInstance> portalInstances = new ArrayList<>();

		for (Company company : companies) {
			if (skipDefaultCompany &&
				(_portalInstancesLocalService.getDefaultCompanyId() ==
					company.getCompanyId())) {

				continue;
			}

			portalInstances.add(_getPortalInstance(company));
		}

		return Page.of(portalInstances);
	}

	@Override
	public PortalInstance patchPortalInstance(
			String webId, PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.getCompanyByWebId(webId);

		String domain = portalInstance.getDomain();
		String virtualHostname = portalInstance.getVirtualhost();

		if (((domain == null) || domain.equals(company.getMx())) &&
			((virtualHostname == null) ||
			 virtualHostname.equals(company.getVirtualHostname()))) {

			throw new ValidationException(
				"Provide a valid new value for one of the modifiable fields, " +
					"domain or virtual host");
		}

		if (domain == null) {
			domain = company.getMx();
		}

		if (virtualHostname == null) {
			virtualHostname = company.getVirtualHostname();
		}

		return _getPortalInstance(
			_companyLocalService.updateCompany(
				company.getCompanyId(), virtualHostname, domain,
				company.getMaxUsers(), company.isActive()));
	}

	@Override
	public PortalInstance postPortalInstance(PortalInstance portalInstance)
		throws Exception {

		Company company = _companyLocalService.addCompany(
			portalInstance.getWebId(), portalInstance.getVirtualhost(),
			portalInstance.getDomain(), false, 0, true);

		_portalInstancesLocalService.initializePortalInstance(
			ServletContextPool.get(StringPool.BLANK), company.getWebId());

		_portalInstancesLocalService.synchronizePortalInstances();

		return _getPortalInstance(company);
	}

	@Override
	public void putPortalInstanceActivate(String webId) throws Exception {
		Company company = _companyLocalService.getCompanyByWebId(webId);

		if (company.isActive()) {
			throw new ValidationException("Portal instance already active");
		}

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), true);
	}

	@Override
	public void putPortalInstanceDeactivate(String webId) throws Exception {
		Company company = _companyLocalService.getCompanyByWebId(webId);

		if (_portalInstancesLocalService.getDefaultCompanyId() ==
				company.getCompanyId()) {

			throw new ValidationException(
				"Select other default portal instance before deactivating it");
		}

		if (!company.isActive()) {
			throw new ValidationException(
				"Portal instance already deactivated");
		}

		_companyLocalService.updateCompany(
			company.getCompanyId(), company.getVirtualHostname(),
			company.getMx(), company.getMaxUsers(), false);
	}

	private PortalInstance _getPortalInstance(Company company) {
		PortalInstance portalInstance = new PortalInstance();

		portalInstance.setActive(company.isActive());
		portalInstance.setCompanyId(company.getCompanyId());
		portalInstance.setDomain(company.getMx());
		portalInstance.setVirtualhost(company.getVirtualHostname());
		portalInstance.setWebId(company.getWebId());

		return portalInstance;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}