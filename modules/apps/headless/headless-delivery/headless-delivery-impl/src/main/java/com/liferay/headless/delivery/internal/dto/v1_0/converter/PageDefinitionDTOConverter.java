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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.ColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.LookAndFeel;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.RowDefinition;
import com.liferay.headless.delivery.dto.v1_0.SectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = {DTOConverter.class, PageDefinitionDTOConverter.class}
)
public class PageDefinitionDTOConverter
	implements DTOConverter<LayoutPageTemplateEntry, PageDefinition> {

	@Override
	public String getContentType() {
		return PageDefinition.class.getSimpleName();
	}

	@Override
	public PageDefinition toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
				(Long)dtoConverterContext.getId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateEntry.
						getLayoutPageTemplateCollectionId());

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		return new PageDefinition() {
			{
				collectionName = layoutPageTemplateCollection.getName();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(
						layoutPageTemplateEntry.getUserId()));
				dateCreated = layoutPageTemplateEntry.getCreateDate();
				dateModified = layoutPageTemplateEntry.getModifiedDate();
				friendlyURLPath = layout.getFriendlyURL(
					dtoConverterContext.getLocale());
				id = layoutPageTemplateEntry.getLayoutPageTemplateEntryId();
				name = layoutPageTemplateEntry.getName();
				pageElements = _toPageElements(
					layoutPageTemplateEntry, layout,
					dtoConverterContext.getLocale());
				settings = _toSettings(layout, dtoConverterContext.getLocale());
				uuid = layoutPageTemplateEntry.getUuid();
			}
		};
	}

	private String _getFragmentCollectionName(
		FragmentEntry fragmentEntry, String rendererKey, Locale locale) {

		if (fragmentEntry == null) {
			if (Validator.isNull(rendererKey)) {
				rendererKey =
					FragmentRendererConstants.
						FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
			}

			FragmentRenderer fragmentRenderer =
				_fragmentRendererTracker.getFragmentRenderer(rendererKey);

			return LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					locale, fragmentRenderer.getClass()),
				"fragment.collection.label." +
					fragmentRenderer.getCollectionKey());
		}

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentEntry.getFragmentCollectionId());

		if (fragmentCollection != null) {
			return fragmentCollection.getName();
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		String[] parts = StringUtil.split(rendererKey, StringPool.DASH);

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			if (Objects.equals(
					fragmentCollectionContributor.getFragmentCollectionKey(),
					parts[0])) {

				return fragmentCollectionContributor.getName(locale);
			}
		}

		return null;
	}

	private FragmentEntry _getFragmentEntry(
		long fragmentEntryId, String rendererKey, Locale locale) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(locale);

		return fragmentEntries.get(rendererKey);
	}

	private String _getFragmentName(
		FragmentEntry fragmentEntry, String portletId, String rendererKey,
		Locale locale) {

		if (fragmentEntry != null) {
			return fragmentEntry.getName();
		}

		if (Validator.isNotNull(portletId)) {
			return _portal.getPortletTitle(portletId, locale);
		}

		if (Validator.isNull(rendererKey)) {
			rendererKey =
				FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(rendererKey);

		return fragmentRenderer.getLabel(locale);
	}

	private ColumnDefinition _toColumnDefinition(JSONObject configJSONObject) {
		return new ColumnDefinition() {
			{
				setSize(
					() -> {
						if (configJSONObject.isNull("size")) {
							return null;
						}

						return configJSONObject.getInt("size");
					});
			}
		};
	}

	private FragmentDefinition _toFragmentDefinition(
			JSONObject configJSONObject, Locale locale)
		throws JSONException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				GetterUtil.getLong(
					configJSONObject.getString("fragmentEntryLinkId")));

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		String portletId = editableValuesJSONObject.getString("portletId");

		String rendererKey = fragmentEntryLink.getRendererKey();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentEntryLink.getFragmentEntryId(), rendererKey, locale);

		return new FragmentDefinition() {
			{
				setFragmentCollectionName(
					_getFragmentCollectionName(
						fragmentEntry, rendererKey, locale));
				setFragmentConfig(
					_toMap(
						_fragmentEntryConfigurationParser.
							getConfigurationJSONObject(
								fragmentEntryLink.getConfiguration(),
								fragmentEntryLink.getEditableValues(),
								new long[] {0L})));
				setFragmentName(
					_getFragmentName(
						fragmentEntry, portletId, rendererKey, locale));
			}
		};
	}

	private LookAndFeel _toLookAndFeel(
		UnicodeProperties typeSettingsProperties) {

		UnicodeProperties themeSettingsProperties = new UnicodeProperties();

		for (Map.Entry<String, String> entry :
				typeSettingsProperties.entrySet()) {

			String key = entry.getKey();

			if (key.startsWith("lfr-theme:")) {
				themeSettingsProperties.setProperty(key, entry.getValue());
			}
		}

		return new LookAndFeel() {
			{
				setThemeSettings(themeSettingsProperties);
			}
		};
	}

	private Map<String, Object> _toMap(
		JSONObject configurationValuesJSONObject) {

		return new HashMap<String, Object>() {
			{
				Set<String> keys = configurationValuesJSONObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					put(key, configurationValuesJSONObject.get(key));
				}
			}
		};
	}

	private MasterPage _toMasterPage(long masterLayoutPlid, Locale locale) {
		Layout masterLayout = _layoutLocalService.fetchLayout(masterLayoutPlid);

		if (masterLayout != null) {
			return new MasterPage() {
				{
					setName(masterLayout.getName(locale));
				}
			};
		}

		return null;
	}

	private PageElement _toPageElement(
			JSONObject jsonObject, JSONObject itemsJSONObject, Locale locale)
		throws JSONException {

		List<PageElement> childrenPageElements = new ArrayList<>();

		JSONArray childrenJSONArray = jsonObject.getJSONArray("children");

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			String childUUID = childrenJSONArray.getString(i);

			JSONObject childJSONObject = itemsJSONObject.getJSONObject(
				childUUID);

			JSONArray grandChildrenJSONArray = childJSONObject.getJSONArray(
				"children");

			if (grandChildrenJSONArray.length() == 0) {
				childrenPageElements.add(
					_toPageElement(
						childJSONObject.getJSONObject("config"),
						childJSONObject.getString("type"), locale));
			}
			else {
				childrenPageElements.add(
					_toPageElement(childJSONObject, itemsJSONObject, locale));
			}
		}

		PageElement pageElement = _toPageElement(
			jsonObject.getJSONObject("config"), jsonObject.getString("type"),
			locale);

		pageElement.setPageElements(
			childrenPageElements.toArray(new PageElement[0]));

		return pageElement;
	}

	private PageElement _toPageElement(
			JSONObject configJSONObject, String type, Locale locale)
		throws JSONException {

		if (type.equals("column")) {
			return new PageElement() {
				{
					setDefinition(_toColumnDefinition(configJSONObject));
					setType(PageElement.Type.COLUMN);
				}
			};
		}
		else if (type.equals("container")) {
			return new PageElement() {
				{
					setDefinition(_toSectionDefinition(configJSONObject));
					setType(PageElement.Type.SECTION);
				}
			};
		}
		else if (type.equals("fragment")) {
			return new PageElement() {
				{
					setDefinition(
						_toFragmentDefinition(configJSONObject, locale));
					setType(PageElement.Type.FRAGMENT);
				}
			};
		}
		else if (type.equals("row")) {
			return new PageElement() {
				{
					setDefinition(_toRowDefinition(configJSONObject));
					setType(PageElement.Type.ROW);
				}
			};
		}

		return null;
	}

	private PageElement[] _toPageElements(
			LayoutPageTemplateEntry layoutPageTemplateEntry, Layout layout,
			Locale locale)
		throws JSONException {

		List<PageElement> pageElements = new ArrayList<>();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry.getGroupId(),
					_portal.getClassNameId(Layout.class), layout.getPlid());

		String layoutData = LayoutDataConverter.convert(
			layoutPageTemplateStructure.getData(0L));

		JSONObject layoutDataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutData);

		JSONObject rootItemsJSONObject = layoutDataJSONObject.getJSONObject(
			"rootItems");

		String mainUUID = rootItemsJSONObject.getString("main");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		JSONObject mainJSONObject = itemsJSONObject.getJSONObject(mainUUID);

		JSONArray childrenJSONArray = mainJSONObject.getJSONArray("children");

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			String childUUID = childrenJSONArray.getString(i);

			pageElements.add(
				_toPageElement(
					itemsJSONObject.getJSONObject(childUUID), itemsJSONObject,
					locale));
		}

		return pageElements.toArray(new PageElement[0]);
	}

	private RowDefinition _toRowDefinition(JSONObject configJSONObject) {
		return new RowDefinition() {
			{
				setGutters(
					() -> {
						if (configJSONObject.isNull("gutters")) {
							return null;
						}

						return configJSONObject.getBoolean("gutters");
					});
				setNumberOfColumns(
					() -> {
						if (configJSONObject.isNull("numberOfColumns")) {
							return null;
						}

						return configJSONObject.getInt("numberOfColumns");
					});
			}
		};
	}

	private SectionDefinition _toSectionDefinition(
		JSONObject configJSONObject) {

		JSONObject backgroundImageJSONObject = configJSONObject.getJSONObject(
			"backgroundImage");

		return new SectionDefinition() {
			{
				setBackgroundColorCssClass(
					configJSONObject.getString(
						"backgroundColorCssClass", null));

				setBackgroundImage(
					new FragmentImage() {
						{
							setTitle(
								_toValueMap(
									backgroundImageJSONObject, "title"));
							setUrl(
								_toValueMap(backgroundImageJSONObject, "url"));
						}
					});

				setPaddingBottom(
					() -> {
						if (configJSONObject.isNull("paddingBottom")) {
							return null;
						}

						return configJSONObject.getInt("paddingBottom");
					});

				setPaddingHorizontal(
					() -> {
						if (configJSONObject.isNull("paddingHorizontal")) {
							return null;
						}

						return configJSONObject.getInt("paddingHorizontal");
					});

				setPaddingTop(
					() -> {
						if (configJSONObject.isNull("paddingTop")) {
							return null;
						}

						return configJSONObject.getInt("paddingTop");
					});

				setContainerType(
					ContainerType.valueOf(
						StringUtil.toUpperCase(
							configJSONObject.getString("type"))));
			}
		};
	}

	private Settings _toSettings(Layout layout, Locale locale) {
		return new Settings() {
			{
				setLookAndFeel(
					_toLookAndFeel(layout.getTypeSettingsProperties()));
				setMasterPage(
					_toMasterPage(layout.getMasterLayoutPlid(), locale));
			}
		};
	}

	private Map<String, String> _toValueMap(
		JSONObject backgroundImageJSONObject, String name) {

		if (backgroundImageJSONObject == null) {
			return null;
		}

		return HashMapBuilder.put(
			"value", backgroundImageJSONObject.getString(name)
		).build();
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}