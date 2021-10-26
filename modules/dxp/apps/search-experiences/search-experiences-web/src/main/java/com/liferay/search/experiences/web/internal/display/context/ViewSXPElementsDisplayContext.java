/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.search.Field;;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPElementEntryPermission;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPElementsDisplayContext
	extends BaseDisplayContext<SXPElement> {

	public ViewSXPElementsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Queries queries,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		SXPElementService sxpElementService) {

		super(
			liferayPortletRequest, liferayPortletResponse, queries, searcher,
			searchRequestBuilderFactory, sorts);

		_sxpElementService = sxpElementService;
	}

	private SXPElementService _sxpElementService;

	public List<String> getAvailableActions(SXPElement sxpElement)
		throws PortalException {

		if (SXPElementEntryPermission.contains(
				themeDisplay.getPermissionChecker(), sxpElement,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteSXPElements");
		}

		return Collections.emptyList();
	}

	public SearchContainer<SXPElement> getSearchContainer()
		throws PortalException {

		return getSearchContainer(
			"no-elements-were-found", WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected String getDisplayStylePreferenceName() {
		return "sxp-elements-display-style";
	}

	@Override
	protected Class<?> getModelIndexerClass() {
		return SXPElement.class;
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/sxp_blueprint_admin/view_sxp_elements";
	}

	@Override
	protected void processBooleanQuery(
		BooleanQuery booleanQuery, Queries queries) {

		int type = ParamUtil.getInteger(liferayPortletRequest, "sxpElementType");

		if (type > 0) {
			booleanQuery.addFilterQueryClauses(queries.term(Field.TYPE, type));
		}

		if (ParamUtil.getString(liferayPortletRequest, "hidden") != null) {
			booleanQuery.addFilterQueryClauses(
				queries.term(
					"hidden", ParamUtil.getBoolean(liferayPortletRequest, "hidden")));
		}

		if (!Validator.isBlank(
				ParamUtil.getString(liferayPortletRequest, "readOnly"))) {

			booleanQuery.addFilterQueryClauses(
				queries.term(
					"readOnly",
					ParamUtil.getBoolean(liferayPortletRequest, "readOnly")));
		}
	}

	@Override
	protected SXPElement toBaseModel(long entryClassPK) {
		try {
			return _sxpElementService.getSXPElement(entryClassPK);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Search index is stale and contains a non-existent " +
						"SXPElement entry " + entryClassPK);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewSXPElementsDisplayContext.class);

}