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

package com.liferay.structured.content.apio.architect.sort;

import java.util.List;

/**
 * Models a Sort Query.
 *
 * @author Cristina González
 * @review
 */
public class SortQuery {

	/**
	 * Creates a new sort query from a list of sort keys.
	 *
	 * @param  sortKeys - list of sort keys
	 * @review
	 */
	public SortQuery(List<Sort.SortKey> sortKeys) {
		_sortKeys = sortKeys;
	}

	/**
	 * Returns the list of sort keys.
	 *
	 * @return - the list of sort keys
	 * @review
	 */
	public List<Sort.SortKey> getSortKeys() {
		return _sortKeys;
	}

	private final List<Sort.SortKey> _sortKeys;

}