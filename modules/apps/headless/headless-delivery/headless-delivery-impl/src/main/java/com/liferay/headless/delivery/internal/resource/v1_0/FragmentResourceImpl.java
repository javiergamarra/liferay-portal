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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.FragmentResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/fragment.properties",
	scope = ServiceScope.PROTOTYPE, service = FragmentResource.class
)
public class FragmentResourceImpl extends BaseFragmentResourceImpl {

	@Override
	public Fragment getFragment(Long fragmentId) throws Exception {
		return _toFragment(
			_fragmentEntryLocalService.getFragmentEntry(fragmentId));
	}

	@Override
	public Page<Fragment> getFragmentCollectionFragmentsPage(
		Long fragmentCollectionId, Pagination pagination) {

		return Page.of(
			transform(
				_fragmentEntryLocalService.getFragmentEntries(
					fragmentCollectionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toFragment),
			pagination,
			_fragmentEntryLocalService.getFragmentEntriesCount(
				fragmentCollectionId));
	}

	private Fragment _toFragment(FragmentEntry fragmentEntry)
		throws PortalException {

		return new Fragment() {
			{
				configuration = JSONFactoryUtil.createJSONObject(
					fragmentEntry.getConfiguration());
				content = fragmentEntry.getContent();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(fragmentEntry.getUserId()));
				css = fragmentEntry.getCss();
				dateCreated = fragmentEntry.getCreateDate();
				dateModified = fragmentEntry.getModifiedDate();
				html = fragmentEntry.getHtml();
				id = fragmentEntry.getFragmentEntryId();
				js = fragmentEntry.getJs();
				name = fragmentEntry.getName();
				usageCount = fragmentEntry.getUsageCount();
			}
		};
	}

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}