package com.liferay.portal.vulcan.batch;

import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface VulcanBatchEngineTaskItemDelegate<T> {

	public default void create(
		Collection<T> items, Map<String, Serializable> parameters)
		throws Exception {
	}

	public default void delete(
		Collection<T> items, Map<String, Serializable> parameters)
		throws Exception {
	}

	public default EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {
		return null;
	}

	public default Page<T> read(
		Filter filter, Pagination pagination, Sort[] sorts,
		Map<String, Serializable> parameters, String search)
		throws Exception {
		return null;
	}

	public default void setContextCompany(Company contextCompany) {
	}

	public default void setContextUser(User contextUser) {
	}

	public default void setLanguageId(String languageId) {
	}

	public default void update(
		Collection<T> items, Map<String, Serializable> parameters)
		throws Exception {
	}
}
