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

package com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade.v1_0_0;

import com.liferay.adaptive.media.document.library.thumbnails.internal.util.AMCompanyThumbnailConfigurationInitializer;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Roberto Díaz
 */
public class UpgradeDocumentLibraryThumbnailsConfiguration
	extends UpgradeProcess {

	public UpgradeDocumentLibraryThumbnailsConfiguration(
		AMCompanyThumbnailConfigurationInitializer
			amCompanyThumbnailConfigurationInitializer,
		CompanyLocalService companyLocalService) {

		_amCompanyThumbnailConfigurationInitializer =
			amCompanyThumbnailConfigurationInitializer;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_companyLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Company company) -> {
					try {
						_amCompanyThumbnailConfigurationInitializer.
							initializeCompany(company);
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				});

			actionableDynamicQuery.performActions();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryThumbnailsConfiguration.class);

	private final AMCompanyThumbnailConfigurationInitializer
		_amCompanyThumbnailConfigurationInitializer;
	private final CompanyLocalService _companyLocalService;

}