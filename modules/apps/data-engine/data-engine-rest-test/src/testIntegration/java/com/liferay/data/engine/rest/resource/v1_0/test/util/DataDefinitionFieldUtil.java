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

package com.liferay.data.engine.rest.resource.v1_0.test.util;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class DataDefinitionFieldUtil {

	public static DataDefinitionField[] createAllDataDefinitionFieldTypes()
		throws Exception {

		List<DataDefinitionField> dataDefinitionFields = new ArrayList<>();

		Map<String, Object> customProperties = new HashMap<String, Object>() {
			{
				put("showAsSwitcher", true);
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomBoolean()),
				"checkbox"));

		customProperties = new HashMap<String, Object>() {
			{
				put("inline", true);
				put("showAsSwitcher", true);
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties, _createLocalizedValues("Option 1"),
				"checkbox_multiple"));

		customProperties = new HashMap<String, Object>() {
			{
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.nextDate()), "date"));

		customProperties = new HashMap<String, Object>() {
			{
				put("groupId", RandomTestUtil.randomLong());
				put("itemSelectorAuthToken", RandomTestUtil.randomString());
				put("lexiconIconsPath", RandomTestUtil.randomString());
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"document_library"));

		customProperties = new HashMap<String, Object>() {
			{
				put(
					"placeholder",
					_createLocalizedValues(RandomTestUtil.randomString()));
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"editor"));

		customProperties = new HashMap<String, Object>() {
			{
				put("orientation", "vertical");
				put("showLabel", true);
			}
		};

		DataDefinitionField fieldSetField = createDataDefinitionField(
			customProperties,
			_createLocalizedValues(RandomTestUtil.randomString()), "fieldset");

		fieldSetField.setNestedDataDefinitionFields(_createNestedFields());

		dataDefinitionFields.add(fieldSetField);

		customProperties = new HashMap<String, Object>() {
			{
				put("dataType", "integer");
				put(
					"placeholder",
					_createLocalizedValues(RandomTestUtil.randomString()));
				put(
					"tooltip",
					_createLocalizedValues(RandomTestUtil.randomString()));
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.nextInt()), "numeric"));

		customProperties = new HashMap<String, Object>() {
			{
				put("showLabel", true);
				put(
					"text",
					_createLocalizedValues(RandomTestUtil.randomString()));
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"paragraph"));

		customProperties = new HashMap<String, Object>() {
			{
				put(
					"placeholder",
					_createLocalizedValues(RandomTestUtil.randomString()));
				put("showLabel", true);
				put(
					"tooltip",
					_createLocalizedValues(RandomTestUtil.randomString()));
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"password"));

		customProperties = new HashMap<String, Object>() {
			{
				put("inline", true);
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"radio"));

		customProperties = new HashMap<String, Object>() {
			{
				put("dataSourceType", "manual");
				put("multiple", true);
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()),
				"select"));

		customProperties = new HashMap<String, Object>() {
			{
				put(
					"placeholder",
					_createLocalizedValues(RandomTestUtil.randomString()));
				put(
					"tooltip",
					_createLocalizedValues(RandomTestUtil.randomString()));
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomString()), "text"));

		return dataDefinitionFields.toArray(new DataDefinitionField[0]);
	}

	public static DataDefinitionField createDataDefinitionField(
		Map<String, Object> customProperties, Map<String, Object> defaultValue,
		String fieldType) {

		DataDefinitionField dataDefinitionField = new DataDefinitionField() {
			{
				indexable = true;
				label = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				localizable = false;
				name = RandomTestUtil.randomString();
				repeatable = false;
				tip = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				id = Long.valueOf(0);
			}
		};

		dataDefinitionField.setCustomProperties(customProperties);
		dataDefinitionField.setDefaultValue(defaultValue);
		dataDefinitionField.setFieldType(fieldType);

		return dataDefinitionField;
	}

	private static Map<String, Object> _createLocalizedValues(Object value) {
		return new HashMap<String, Object>() {
			{
				put("en_US", value);
			}
		};
	}

	private static DataDefinitionField[] _createNestedFields() {
		List<DataDefinitionField> dataDefinitionFields = new ArrayList<>();

		Map<String, Object> customProperties = new HashMap<String, Object>() {
			{
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.nextDate()), "date"));

		customProperties = new HashMap<String, Object>() {
			{
				put("showAsSwitcher", true);
				put("showLabel", true);
			}
		};

		dataDefinitionFields.add(
			createDataDefinitionField(
				customProperties,
				_createLocalizedValues(RandomTestUtil.randomBoolean()),
				"checkbox"));

		return dataDefinitionFields.toArray(new DataDefinitionField[0]);
	}

}