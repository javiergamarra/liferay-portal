/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet.config;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Raymond Augé
 */
public class FacetConfiguration {

	public String getClassName() {
		return _className;
	}

	public JSONObject getData() {
		if (_data == null) {
			_data = JSONFactoryUtil.createJSONObject();
		}

		return _data;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getLabel() {
		return _label;
	}

	public String getOrder() {
		if (_order == null) {
			return "OrderHitsDesc";
		}

		return _order;
	}

	public double getWeight() {
		return _weight;
	}

	public boolean isStatic() {
		return _static;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setData(JSONObject data) {
		_data = data;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setOrder(String order) {
		_order = order;
	}

	public void setStatic(boolean isStatic) {
		_static = isStatic;
	}

	public void setWeight(double weight) {
		_weight = weight;
	}

	private String _className;
	private JSONObject _data;
	private String _displayStyle;
	private String _fieldName;
	private String _label;
	private String _order;
	private boolean _static;
	private double _weight;

}