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

package com.liferay.fragment.util.comparator;

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class FragmentCompositionAndEntryCreateDateComparator
	extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public FragmentCompositionAndEntryCreateDateComparator() {
		this(true);
	}

	public FragmentCompositionAndEntryCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		int value = 0;

		if ((object1 instanceof FragmentComposition) &&
			(object2 instanceof FragmentComposition)) {

			FragmentComposition fragmentComposition1 =
				(FragmentComposition)object1;

			FragmentComposition fragmentComposition2 =
				(FragmentComposition)object2;

			DateUtil.compareTo(
				fragmentComposition1.getCreateDate(),
				fragmentComposition2.getCreateDate());
		}
		else {
			FragmentEntry fragmentEntry1 = (FragmentEntry)object1;
			FragmentEntry fragmentEntry2 = (FragmentEntry)object2;

			DateUtil.compareTo(
				fragmentEntry1.getCreateDate(), fragmentEntry2.getCreateDate());
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}