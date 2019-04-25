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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class LocalizedValueUtil {

	public static Object getLocalizedValue(
		Locale locale, Map<String, ?> localizedValues) {

		return localizedValues.get(LocaleUtil.toLanguageId(locale));
	}

	public static JSONObject toJSONObject(Map<String, ?> localizedValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(localizedValues)) {
			return jsonObject;
		}

		for (Map.Entry<String, ?> entry : localizedValues.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static Map<String, String> toLocalizedStrings(
		JSONObject jsonObject) {

		Map<String, String> localizedValues = new HashMap<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			localizedValues.put(key, jsonObject.getString(key));
		}

		return localizedValues;
	}

	public static Map<String, String> toLocalizedStrings(
		Map<Locale, String> localizationMap) {

		Map<String, String> localizedValues = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			localizedValues.put(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}

		return localizedValues;
	}

	public static Map<Locale, String> toLocalizedValues(
		Map<String, ?> localizedValues) {

		if (MapUtil.isEmpty(localizedValues)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> localizationMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : localizedValues.entrySet()) {
			localizationMap.put(
				LocaleUtil.fromLanguageId(entry.getKey()),
				(String)entry.getValue());
		}

		return localizationMap;
	}

}