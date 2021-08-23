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

import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.admin.content.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.dto.v1_0.util.ContentStructureUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-structure.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentStructureResource.class
)
public class ContentStructureResourceImpl
	extends BaseContentStructureResourceImpl {

	//	implements EntityModelResource

	@Override
	public void deleteContentStructure(Long contentStructureId)
		throws Exception {

		DataDefinitionResource.Builder builder =
			_dataDefinitionResourceFactory.create();

		builder.user(contextUser);

		DataDefinitionResource dataDefinitionResource = builder.build();

		dataDefinitionResource.deleteDataDefinition(contentStructureId);
	}

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Override
	public Page<ContentStructure> getAssetLibraryContentStructuresPage(
			Long assetLibraryId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteContentStructuresPage(
			assetLibraryId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public ContentStructure getContentStructure(Long contentStructureId)
		throws Exception {

		return _toContentStructure(
			_ddmStructureService.getStructure(contentStructureId));
	}

	@Override
	public Page<ContentStructure> getSiteContentStructuresPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			filter, DDMStructure.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toContentStructure(
				_ddmStructureService.getStructure(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ContentStructure patchContentStructure(
			Long contentStructureId, ContentStructure contentStructure)
		throws Exception {

		return super.patchContentStructure(
			contentStructureId, contentStructure);
	}

	@Override
	public ContentStructure postAssetLibraryContentStructure(
			Long siteId, ContentStructure contentStructure)
		throws Exception {

		return super.postAssetLibraryContentStructure(siteId, contentStructure);
	}

	@Reference
	private DataDefinitionResource _dataDefinitionResource;

	@Override
	public ContentStructure postSiteContentStructure(
			Long siteId, ContentStructure contentStructure)
		throws Exception {

		return super.postSiteContentStructure(siteId, contentStructure);

		////			HashMapBuilder.put(
		////				LocaleUtil.getSiteDefault(), "Test Structure"
		////			).build(),
		//			null, ddmForm, ddmFormLayout, StorageType.DEFAULT.getValue(),
		//			DDMStructureConstants.TYPE_DEFAULT, new ServiceContext());
		//
		//		DDMForm ddmForm,
		////			DDMFormLayout ddmFormLayout, String storageType,
		////			ServiceContext serviceContext
		//
		//		return _toContentStructure(_ddmStructureService.addStructure(siteId, _portal.getClassNameId(DDMFormInstance.class.getName()),
		//			LocalizedMapUtil.getLocalizedMap(contentStructure.getName_i18n()),
		//			LocalizedMapUtil.getLocalizedMap(contentStructure.getDescription_i18n()),
		//			null, null, StorageType.DEFAULT.getValue(), new ServiceContext()
		//			));

	}

	@Override
	public ContentStructure putContentStructure(
			Long contentStructureId, ContentStructure contentStructure)
		throws Exception {

		_ddmStructureService

		return super.putContentStructure(contentStructureId, contentStructure);
	}

	private ContentStructure _toContentStructure(DDMStructure ddmStructure) {
		return ContentStructureUtil.toContentStructure(
			contextAcceptLanguage.isAcceptAllLanguages(), groupLocalService,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService, ddmStructure);
	}

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}