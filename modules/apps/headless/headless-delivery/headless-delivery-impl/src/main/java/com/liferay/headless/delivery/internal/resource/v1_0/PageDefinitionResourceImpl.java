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

import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.PageDefinitionDTOConverter;
import com.liferay.headless.delivery.resource.v1_0.PageDefinitionResource;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = PageDefinitionResource.class
)
public class PageDefinitionResourceImpl extends BasePageDefinitionResourceImpl {

	@Override
	public PageDefinition getPageTemplate(Long pageTemplateId)
		throws Exception {

		return _toPageDefinition(
			_layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
				pageTemplateId));
	}

	private PageDefinition _toPageDefinition(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		return _pageDefinitionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry,
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private PageDefinitionDTOConverter _pageDefinitionDTOConverter;

}