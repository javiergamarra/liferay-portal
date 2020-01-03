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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentEntryLinkSerDes {

	public static FragmentEntryLink toDTO(String json) {
		FragmentEntryLinkJSONParser fragmentEntryLinkJSONParser =
			new FragmentEntryLinkJSONParser();

		return fragmentEntryLinkJSONParser.parseToDTO(json);
	}

	public static FragmentEntryLink[] toDTOs(String json) {
		FragmentEntryLinkJSONParser fragmentEntryLinkJSONParser =
			new FragmentEntryLinkJSONParser();

		return fragmentEntryLinkJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentEntryLink fragmentEntryLink) {
		if (fragmentEntryLink == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentEntryLink.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getConfiguration()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getContent()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getCss()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					fragmentEntryLink.getDateCreated()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					fragmentEntryLink.getDateModified()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getDescription()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getEditableValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"editableValues\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getEditableValues()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getHtml() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"html\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getHtml()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(fragmentEntryLink.getId());
		}

		if (fragmentEntryLink.getJs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"js\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getJs()));

			sb.append("\"");
		}

		if (fragmentEntryLink.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fragmentEntryLink.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentEntryLinkJSONParser fragmentEntryLinkJSONParser =
			new FragmentEntryLinkJSONParser();

		return fragmentEntryLinkJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentEntryLink fragmentEntryLink) {

		if (fragmentEntryLink == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (fragmentEntryLink.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(fragmentEntryLink.getConfiguration()));
		}

		if (fragmentEntryLink.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(fragmentEntryLink.getContent()));
		}

		if (fragmentEntryLink.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(fragmentEntryLink.getCss()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(fragmentEntryLink.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				fragmentEntryLink.getDateModified()));

		if (fragmentEntryLink.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(fragmentEntryLink.getDescription()));
		}

		if (fragmentEntryLink.getEditableValues() == null) {
			map.put("editableValues", null);
		}
		else {
			map.put(
				"editableValues",
				String.valueOf(fragmentEntryLink.getEditableValues()));
		}

		if (fragmentEntryLink.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(fragmentEntryLink.getHtml()));
		}

		if (fragmentEntryLink.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(fragmentEntryLink.getId()));
		}

		if (fragmentEntryLink.getJs() == null) {
			map.put("js", null);
		}
		else {
			map.put("js", String.valueOf(fragmentEntryLink.getJs()));
		}

		if (fragmentEntryLink.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fragmentEntryLink.getName()));
		}

		return map;
	}

	public static class FragmentEntryLinkJSONParser
		extends BaseJSONParser<FragmentEntryLink> {

		@Override
		protected FragmentEntryLink createDTO() {
			return new FragmentEntryLink();
		}

		@Override
		protected FragmentEntryLink[] createDTOArray(int size) {
			return new FragmentEntryLink[size];
		}

		@Override
		protected void setField(
			FragmentEntryLink fragmentEntryLink, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setConfiguration(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "editableValues")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setEditableValues(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setHtml((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setJs((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fragmentEntryLink.setName((String)jsonParserFieldValue);
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