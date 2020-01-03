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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.LayoutResource;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.LayoutTemplateLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/layout.properties",
	scope = ServiceScope.PROTOTYPE, service = LayoutResource.class
)
public class LayoutResourceImpl extends BaseLayoutResourceImpl {

	@Override
	public Page<Layout> getSiteLayoutsPage(Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_layoutService.getLayouts(
					siteId, false, 0, true, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toLayout),
			pagination, _layoutService.getLayoutsCount(siteId, false, 0));
	}

	private Object _deserialize(String configuration) {
		if ((configuration == null) || configuration.equals("")) {
			return null;
		}

		return JSONFactoryUtil.looseDeserialize(configuration);
	}

	private FragmentEntryLink _toFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {

		return new FragmentEntryLink() {
			{
				configuration = _deserialize(
					fragmentEntryLink.getConfiguration());
				css = fragmentEntryLink.getCss();
				dateCreated = fragmentEntryLink.getCreateDate();
				dateModified = fragmentEntryLink.getModifiedDate();
				editableValues = _deserialize(
					fragmentEntryLink.getEditableValues());
				html = fragmentEntryLink.getHtml();
				id = fragmentEntryLink.getFragmentEntryLinkId();
				js = fragmentEntryLink.getJs();
			}
		};
	}

	private Layout _toLayout(com.liferay.portal.kernel.model.Layout layout)
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			com.liferay.portal.kernel.model.Layout.class.getName());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), classNameId, layout.getPlid(), true);

		String settingLayoutTemplateId = layout.getTypeSettingsProperty(
			"layout-template-id");

		return new Layout() {
			{
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(layout.getUserId()));
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				description = layout.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				fragmentEntryLinks = transformToArray(
					_fragmentEntryLinkLocalService.getFragmentEntryLinks(
						layout.getGroupId(), classNameId, layout.getPlid()),
					LayoutResourceImpl.this::_toFragmentEntryLink,
					FragmentEntryLink.class);
				friendlyURL = layout.getFriendlyURL(
					contextAcceptLanguage.getPreferredLocale());
				id = layout.getPlid();
				keywords = layout.getKeywords(
					contextAcceptLanguage.getPreferredLocale());
				layoutData = _deserialize(
					layoutPageTemplateStructure.getData(0));
				layouts = transformToArray(
					layout.getChildren(), LayoutResourceImpl.this::_toLayout,
					Layout.class);
				layoutTemplateId = settingLayoutTemplateId;
				name = layout.getName(
					contextAcceptLanguage.getPreferredLocale());
				siteId = layout.getGroupId();
				type = layout.getType();

				setColumns(
					() -> {
						LayoutTemplate layoutTemplate =
							_layoutTemplateLocalService.getLayoutTemplate(
								settingLayoutTemplateId, false,
								layout.getThemeId());

						if (layoutTemplate == null) {
							return null;
						}

						List<String> columns = layoutTemplate.getColumns();

						Stream<String> stream = columns.stream();

						return stream.map(
							layout::getTypeSettingsProperty
						).toArray(
							String[]::new
						);
					});
			}
		};
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutTemplateLocalService _layoutTemplateLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}