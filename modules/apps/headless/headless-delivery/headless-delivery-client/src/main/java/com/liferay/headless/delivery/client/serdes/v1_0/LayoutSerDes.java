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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentEntryLink;
import com.liferay.headless.delivery.client.dto.v1_0.Layout;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class LayoutSerDes {

	public static Layout toDTO(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToDTO(json);
	}

	public static Layout[] toDTOs(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Layout layout) {
		if (layout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (layout.getColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columns\": ");

			sb.append("[");

			for (int i = 0; i < layout.getColumns().length; i++) {
				sb.append("\"");

				sb.append(_escape(layout.getColumns()[i]));

				sb.append("\"");

				if ((i + 1) < layout.getColumns().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (layout.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(layout.getCreator()));
		}

		if (layout.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(layout.getDateCreated()));

			sb.append("\"");
		}

		if (layout.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(layout.getDateModified()));

			sb.append("\"");
		}

		if (layout.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(layout.getDescription()));

			sb.append("\"");
		}

		if (layout.getFragmentEntryLinks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentEntryLinks\": ");

			sb.append("[");

			for (int i = 0; i < layout.getFragmentEntryLinks().length; i++) {
				sb.append(String.valueOf(layout.getFragmentEntryLinks()[i]));

				if ((i + 1) < layout.getFragmentEntryLinks().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (layout.getFriendlyURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyURL\": ");

			sb.append("\"");

			sb.append(_escape(layout.getFriendlyURL()));

			sb.append("\"");
		}

		if (layout.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(layout.getId());
		}

		if (layout.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("\"");

			sb.append(_escape(layout.getKeywords()));

			sb.append("\"");
		}

		if (layout.getLayoutData() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layoutData\": ");

			sb.append("\"");

			sb.append(_escape(layout.getLayoutData()));

			sb.append("\"");
		}

		if (layout.getLayoutTemplateId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layoutTemplateId\": ");

			sb.append("\"");

			sb.append(_escape(layout.getLayoutTemplateId()));

			sb.append("\"");
		}

		if (layout.getLayouts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layouts\": ");

			sb.append("[");

			for (int i = 0; i < layout.getLayouts().length; i++) {
				sb.append(String.valueOf(layout.getLayouts()[i]));

				if ((i + 1) < layout.getLayouts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (layout.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(layout.getName()));

			sb.append("\"");
		}

		if (layout.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(layout.getSiteId());
		}

		if (layout.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(layout.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LayoutJSONParser layoutJSONParser = new LayoutJSONParser();

		return layoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Layout layout) {
		if (layout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (layout.getColumns() == null) {
			map.put("columns", null);
		}
		else {
			map.put("columns", String.valueOf(layout.getColumns()));
		}

		if (layout.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(layout.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(layout.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(layout.getDateModified()));

		if (layout.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(layout.getDescription()));
		}

		if (layout.getFragmentEntryLinks() == null) {
			map.put("fragmentEntryLinks", null);
		}
		else {
			map.put(
				"fragmentEntryLinks",
				String.valueOf(layout.getFragmentEntryLinks()));
		}

		if (layout.getFriendlyURL() == null) {
			map.put("friendlyURL", null);
		}
		else {
			map.put("friendlyURL", String.valueOf(layout.getFriendlyURL()));
		}

		if (layout.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(layout.getId()));
		}

		if (layout.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(layout.getKeywords()));
		}

		if (layout.getLayoutData() == null) {
			map.put("layoutData", null);
		}
		else {
			map.put("layoutData", String.valueOf(layout.getLayoutData()));
		}

		if (layout.getLayoutTemplateId() == null) {
			map.put("layoutTemplateId", null);
		}
		else {
			map.put(
				"layoutTemplateId",
				String.valueOf(layout.getLayoutTemplateId()));
		}

		if (layout.getLayouts() == null) {
			map.put("layouts", null);
		}
		else {
			map.put("layouts", String.valueOf(layout.getLayouts()));
		}

		if (layout.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(layout.getName()));
		}

		if (layout.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(layout.getSiteId()));
		}

		if (layout.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(layout.getType()));
		}

		return map;
	}

	public static class LayoutJSONParser extends BaseJSONParser<Layout> {

		@Override
		protected Layout createDTO() {
			return new Layout();
		}

		@Override
		protected Layout[] createDTOArray(int size) {
			return new Layout[size];
		}

		@Override
		protected void setField(
			Layout layout, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "columns")) {
				if (jsonParserFieldValue != null) {
					layout.setColumns(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					layout.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					layout.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					layout.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					layout.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "fragmentEntryLinks")) {

				if (jsonParserFieldValue != null) {
					layout.setFragmentEntryLinks(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentEntryLinkSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FragmentEntryLink[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyURL")) {
				if (jsonParserFieldValue != null) {
					layout.setFriendlyURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					layout.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					layout.setKeywords((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layoutData")) {
				if (jsonParserFieldValue != null) {
					layout.setLayoutData((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layoutTemplateId")) {
				if (jsonParserFieldValue != null) {
					layout.setLayoutTemplateId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layouts")) {
				if (jsonParserFieldValue != null) {
					layout.setLayouts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> LayoutSerDes.toDTO((String)object)
						).toArray(
							size -> new Layout[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					layout.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					layout.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					layout.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}