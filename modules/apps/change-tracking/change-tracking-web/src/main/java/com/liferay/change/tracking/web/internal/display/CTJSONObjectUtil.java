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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

/**
 * @author Samuel Trong Tran
 */
public class CTJSONObjectUtil {

	public static JSONObject getUserInfoJSONObject(
		Predicate predicate, ThemeDisplay themeDisplay,
		UserLocalService userLocalService) {

		JSONObject userInfoJSONObject = JSONFactoryUtil.createJSONObject();

		List<User> users = userLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				UserTable.INSTANCE
			).from(
				UserTable.INSTANCE
			).innerJoinON(
				CTEntryTable.INSTANCE,
				CTEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			).where(
				predicate
			));

		for (User user : users) {
			JSONObject userJSONObject = JSONUtil.put(
				"userName", user.getFullName());

			if (user.getPortraitId() != 0) {
				try {
					userJSONObject.put(
						"portraitURL", user.getPortraitURL(themeDisplay));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			userInfoJSONObject.put(
				String.valueOf(user.getUserId()), userJSONObject);
		}

		return userInfoJSONObject;
	}

	private CTJSONObjectUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTJSONObjectUtil.class);

}