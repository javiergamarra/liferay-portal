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

import com.liferay.headless.delivery.client.dto.v1_0.CreatorDetailedInfo;
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
public class CreatorDetailedInfoSerDes {

	public static CreatorDetailedInfo toDTO(String json) {
		CreatorDetailedInfoJSONParser creatorDetailedInfoJSONParser =
			new CreatorDetailedInfoJSONParser();

		return creatorDetailedInfoJSONParser.parseToDTO(json);
	}

	public static CreatorDetailedInfo[] toDTOs(String json) {
		CreatorDetailedInfoJSONParser creatorDetailedInfoJSONParser =
			new CreatorDetailedInfoJSONParser();

		return creatorDetailedInfoJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreatorDetailedInfo creatorDetailedInfo) {
		if (creatorDetailedInfo == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (creatorDetailedInfo.getJoinDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"joinDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					creatorDetailedInfo.getJoinDate()));

			sb.append("\"");
		}

		if (creatorDetailedInfo.getLastPostDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPostDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					creatorDetailedInfo.getLastPostDate()));

			sb.append("\"");
		}

		if (creatorDetailedInfo.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(creatorDetailedInfo.getName()));

			sb.append("\"");
		}

		if (creatorDetailedInfo.getPostsNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postsNumber\": ");

			sb.append(creatorDetailedInfo.getPostsNumber());
		}

		if (creatorDetailedInfo.getRank() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append("\"");

			sb.append(_escape(creatorDetailedInfo.getRank()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreatorDetailedInfoJSONParser creatorDetailedInfoJSONParser =
			new CreatorDetailedInfoJSONParser();

		return creatorDetailedInfoJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreatorDetailedInfo creatorDetailedInfo) {

		if (creatorDetailedInfo == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"joinDate",
			liferayToJSONDateFormat.format(creatorDetailedInfo.getJoinDate()));

		map.put(
			"lastPostDate",
			liferayToJSONDateFormat.format(
				creatorDetailedInfo.getLastPostDate()));

		if (creatorDetailedInfo.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(creatorDetailedInfo.getName()));
		}

		if (creatorDetailedInfo.getPostsNumber() == null) {
			map.put("postsNumber", null);
		}
		else {
			map.put(
				"postsNumber",
				String.valueOf(creatorDetailedInfo.getPostsNumber()));
		}

		if (creatorDetailedInfo.getRank() == null) {
			map.put("rank", null);
		}
		else {
			map.put("rank", String.valueOf(creatorDetailedInfo.getRank()));
		}

		return map;
	}

	public static class CreatorDetailedInfoJSONParser
		extends BaseJSONParser<CreatorDetailedInfo> {

		@Override
		protected CreatorDetailedInfo createDTO() {
			return new CreatorDetailedInfo();
		}

		@Override
		protected CreatorDetailedInfo[] createDTOArray(int size) {
			return new CreatorDetailedInfo[size];
		}

		@Override
		protected void setField(
			CreatorDetailedInfo creatorDetailedInfo, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "joinDate")) {
				if (jsonParserFieldValue != null) {
					creatorDetailedInfo.setJoinDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPostDate")) {
				if (jsonParserFieldValue != null) {
					creatorDetailedInfo.setLastPostDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					creatorDetailedInfo.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postsNumber")) {
				if (jsonParserFieldValue != null) {
					creatorDetailedInfo.setPostsNumber(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rank")) {
				if (jsonParserFieldValue != null) {
					creatorDetailedInfo.setRank((String)jsonParserFieldValue);
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