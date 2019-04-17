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

import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RatingSerDes {

	public static Rating toDTO(String json) {
		RatingJSONParser ratingJSONParser = new RatingJSONParser();

		return ratingJSONParser.parseToDTO(json);
	}

	public static Rating[] toDTOs(String json) {
		RatingJSONParser ratingJSONParser = new RatingJSONParser();

		return ratingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Rating rating) {
		if (rating == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (rating.getBestRating() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"bestRating\":");

			sb.append(rating.getBestRating());
		}

		if (rating.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(rating.getCreator()));
		}

		if (rating.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(rating.getDateCreated()));

			sb.append("\"");
		}

		if (rating.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(rating.getDateModified()));

			sb.append("\"");
		}

		if (rating.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(rating.getId());
		}

		if (rating.getRatingValue() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"ratingValue\":");

			sb.append(rating.getRatingValue());
		}

		if (rating.getWorstRating() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"worstRating\":");

			sb.append(rating.getWorstRating());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Rating rating) {
		if (rating == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (rating.getBestRating() == null) {
			map.put("bestRating", null);
		}
		else {
			map.put("bestRating", String.valueOf(rating.getBestRating()));
		}

		if (rating.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(rating.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(rating.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(rating.getDateModified()));

		if (rating.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(rating.getId()));
		}

		if (rating.getRatingValue() == null) {
			map.put("ratingValue", null);
		}
		else {
			map.put("ratingValue", String.valueOf(rating.getRatingValue()));
		}

		if (rating.getWorstRating() == null) {
			map.put("worstRating", null);
		}
		else {
			map.put("worstRating", String.valueOf(rating.getWorstRating()));
		}

		return map;
	}

	private static class RatingJSONParser extends BaseJSONParser<Rating> {

		@Override
		protected Rating createDTO() {
			return new Rating();
		}

		@Override
		protected Rating[] createDTOArray(int size) {
			return new Rating[size];
		}

		@Override
		protected void setField(
			Rating rating, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bestRating")) {
				if (jsonParserFieldValue != null) {
					rating.setBestRating((Double)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					rating.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					rating.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					rating.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					rating.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingValue")) {
				if (jsonParserFieldValue != null) {
					rating.setRatingValue((Double)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					rating.setWorstRating((Double)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}