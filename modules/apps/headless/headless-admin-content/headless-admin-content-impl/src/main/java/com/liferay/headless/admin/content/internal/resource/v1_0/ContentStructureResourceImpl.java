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
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.admin.content.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.dto.v1_0.util.ContentStructureUtil;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

	@Override
	public void deleteContentStructure(Long contentStructureId)
		throws Exception {

		DataDefinitionResource.Builder builder =
			_dataDefinitionResourceFactory.create();

		builder.user(contextUser);

		DataDefinitionResource dataDefinitionResource = builder.build();

		dataDefinitionResource.deleteDataDefinition(contentStructureId);
	}

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

	@Override
	public ContentStructure postSiteContentStructure(
			Long siteId, ContentStructure contentStructure)
		throws Exception {

		return super.postSiteContentStructure(siteId, contentStructure);
	}

	@Override
	public ContentStructure putContentStructure(
			Long contentStructureId, ContentStructure contentStructure)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			contentStructureId);

		ContentStructureField[] contentStructureFields =
			contentStructure.getContentStructureFields();

		DDMForm ddmForm = new DDMForm();

		Locale preferredLocale = contextAcceptLanguage.getPreferredLocale();

		ddmForm.setAvailableLocales(Collections.singleton(preferredLocale));
		ddmForm.setDefaultLocale(preferredLocale);

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(true);

		for (ContentStructureField contentStructureField :
				contentStructureFields) {

			DDMFormField ddmFormField = _getDDMFormField(
				contentStructureField, ddmFormFields);

			ddmFormField.setDataType(
				_toDataType(contentStructureField.getDataType()));
			ddmFormField.setDDMForm(ddmForm);
			ddmFormField.setFieldReference(contentStructureField.getName());
			ddmFormField.setLabel(
				_toLocalizedValue(contentStructureField.getLabel()));
			ddmFormField.setLocalizable(
				GetterUtil.getBoolean(
					contentStructureField.getLocalizable(),
					ddmFormField.isLocalizable()));
			ddmFormField.setMultiple(
				GetterUtil.getBoolean(
					contentStructureField.getMultiple(),
					ddmFormField.isMultiple()));
			ddmFormField.setName(contentStructureField.getName());
			ddmFormField.setPredefinedValue(
				_toLocalizedValue(contentStructureField.getPredefinedValue()));
			ddmFormField.setRepeatable(
				GetterUtil.getBoolean(
					contentStructureField.getRepeatable(),
					ddmFormField.isRepeatable()));
			ddmFormField.setRequired(
				GetterUtil.getBoolean(
					contentStructureField.getRequired(),
					ddmFormField.isRequired()));
			ddmFormField.setShowLabel(
				GetterUtil.getBoolean(
					contentStructureField.getShowLabel(),
					ddmFormField.isShowLabel()));
			ddmFormField.setType(contentStructureField.getInputControl());

			ddmForm.addDDMFormField(ddmFormField);
		}

		return _toContentStructure(
			_ddmStructureService.updateStructure(
				ddmStructure.getStructureId(),
				ddmStructure.getParentStructureId(),
				Collections.singletonMap(
					preferredLocale, contentStructure.getName()),
				Collections.singletonMap(
					preferredLocale, contentStructure.getDescription()),
				ddmForm, ddmStructure.getDDMFormLayout(),
				new ServiceContext()));
	}

	private DDMFormField _getDDMFormField(
		ContentStructureField contentStructureField,
		List<DDMFormField> ddmFormFields) {

		String name = contentStructureField.getName();

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (name.equals(ddmFormField.getFieldReference())) {
				return ddmFormField;
			}
		}

		return new DDMFormField();
	}

	private ContentStructure _toContentStructure(DDMStructure ddmStructure) {
		return ContentStructureUtil.toContentStructure(
			contextAcceptLanguage.isAcceptAllLanguages(), groupLocalService,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService, ddmStructure);
	}

	private String _toDataType(String dataType) {
		if (dataType.equals("document")) {
			return DDMFormFieldTypeConstants.DOCUMENT_LIBRARY;
		}
		else if (dataType.equals("structuredContent")) {
			return JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE;
		}
		else if (dataType.equals("url")) {
			return LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT;
		}

		return dataType;
	}

	private LocalizedValue _toLocalizedValue(String value) {
		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(
			contextAcceptLanguage.getPreferredLocale(), value);

		return localizedValue;
	}

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}