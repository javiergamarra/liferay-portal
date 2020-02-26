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

import com.liferay.headless.delivery.client.dto.v1_0.MessageCreator;
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
public class MessageCreatorSerDes {

	public static MessageCreator toDTO(String json) {
		MessageCreatorJSONParser messageCreatorJSONParser =
			new MessageCreatorJSONParser();

		return messageCreatorJSONParser.parseToDTO(json);
	}

	public static MessageCreator[] toDTOs(String json) {
		MessageCreatorJSONParser messageCreatorJSONParser =
			new MessageCreatorJSONParser();

		return messageCreatorJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageCreator messageCreator) {
		if (messageCreator == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (messageCreator.getJoinDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"joinDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(messageCreator.getJoinDate()));

			sb.append("\"");
		}

		if (messageCreator.getLastPostDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPostDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageCreator.getLastPostDate()));

			sb.append("\"");
		}

		if (messageCreator.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(messageCreator.getName()));

			sb.append("\"");
		}

		if (messageCreator.getPostsNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postsNumber\": ");

			sb.append(messageCreator.getPostsNumber());
		}

		if (messageCreator.getRank() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append("\"");

			sb.append(_escape(messageCreator.getRank()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MessageCreatorJSONParser messageCreatorJSONParser =
			new MessageCreatorJSONParser();

		return messageCreatorJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MessageCreator messageCreator) {
		if (messageCreator == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"joinDate",
			liferayToJSONDateFormat.format(messageCreator.getJoinDate()));

		map.put(
			"lastPostDate",
			liferayToJSONDateFormat.format(messageCreator.getLastPostDate()));

		if (messageCreator.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(messageCreator.getName()));
		}

		if (messageCreator.getPostsNumber() == null) {
			map.put("postsNumber", null);
		}
		else {
			map.put(
				"postsNumber", String.valueOf(messageCreator.getPostsNumber()));
		}

		if (messageCreator.getRank() == null) {
			map.put("rank", null);
		}
		else {
			map.put("rank", String.valueOf(messageCreator.getRank()));
		}

		return map;
	}

	public static class MessageCreatorJSONParser
		extends BaseJSONParser<MessageCreator> {

		@Override
		protected MessageCreator createDTO() {
			return new MessageCreator();
		}

		@Override
		protected MessageCreator[] createDTOArray(int size) {
			return new MessageCreator[size];
		}

		@Override
		protected void setField(
			MessageCreator messageCreator, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "joinDate")) {
				if (jsonParserFieldValue != null) {
					messageCreator.setJoinDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPostDate")) {
				if (jsonParserFieldValue != null) {
					messageCreator.setLastPostDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					messageCreator.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postsNumber")) {
				if (jsonParserFieldValue != null) {
					messageCreator.setPostsNumber(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rank")) {
				if (jsonParserFieldValue != null) {
					messageCreator.setRank((String)jsonParserFieldValue);
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