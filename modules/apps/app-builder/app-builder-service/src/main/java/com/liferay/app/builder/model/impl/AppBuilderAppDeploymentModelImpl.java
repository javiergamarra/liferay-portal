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

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.model.AppBuilderAppDeploymentModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model implementation for the AppBuilderAppDeployment service. Represents a row in the &quot;AppBuilderAppDeployment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>AppBuilderAppDeploymentModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AppBuilderAppDeploymentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentImpl
 * @generated
 */
@ProviderType
public class AppBuilderAppDeploymentModelImpl
	extends BaseModelImpl<AppBuilderAppDeployment>
	implements AppBuilderAppDeploymentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a app builder app deployment model instance should use the <code>AppBuilderAppDeployment</code> interface instead.
	 */
	public static final String TABLE_NAME = "AppBuilderAppDeployment";

	public static final Object[][] TABLE_COLUMNS = {
		{"appBuilderAppDeploymentId", Types.BIGINT},
		{"appBuilderAppId", Types.BIGINT}, {"deploymentType", Types.VARCHAR},
		{"settings_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("appBuilderAppDeploymentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("appBuilderAppId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("deploymentType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("settings_", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AppBuilderAppDeployment (appBuilderAppDeploymentId LONG not null primary key,appBuilderAppId LONG,deploymentType VARCHAR(75) null,settings_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
		"drop table AppBuilderAppDeployment";

	public static final String ORDER_BY_JPQL =
		" ORDER BY appBuilderAppDeployment.appBuilderAppDeploymentId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AppBuilderAppDeployment.appBuilderAppDeploymentId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long APPBUILDERAPPID_COLUMN_BITMASK = 1L;

	public static final long APPBUILDERAPPDEPLOYMENTID_COLUMN_BITMASK = 2L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public AppBuilderAppDeploymentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _appBuilderAppDeploymentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAppBuilderAppDeploymentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _appBuilderAppDeploymentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AppBuilderAppDeployment.class;
	}

	@Override
	public String getModelClassName() {
		return AppBuilderAppDeployment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AppBuilderAppDeployment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AppBuilderAppDeployment, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AppBuilderAppDeployment, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((AppBuilderAppDeployment)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AppBuilderAppDeployment, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AppBuilderAppDeployment, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AppBuilderAppDeployment)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AppBuilderAppDeployment, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AppBuilderAppDeployment, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AppBuilderAppDeployment>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AppBuilderAppDeployment.class.getClassLoader(),
			AppBuilderAppDeployment.class, ModelWrapper.class);

		try {
			Constructor<AppBuilderAppDeployment> constructor =
				(Constructor<AppBuilderAppDeployment>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<AppBuilderAppDeployment, Object>>
		_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<AppBuilderAppDeployment, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<AppBuilderAppDeployment, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<AppBuilderAppDeployment, Object>>();
		Map<String, BiConsumer<AppBuilderAppDeployment, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<AppBuilderAppDeployment, ?>>();

		attributeGetterFunctions.put(
			"appBuilderAppDeploymentId",
			AppBuilderAppDeployment::getAppBuilderAppDeploymentId);
		attributeSetterBiConsumers.put(
			"appBuilderAppDeploymentId",
			(BiConsumer<AppBuilderAppDeployment, Long>)
				AppBuilderAppDeployment::setAppBuilderAppDeploymentId);
		attributeGetterFunctions.put(
			"appBuilderAppId", AppBuilderAppDeployment::getAppBuilderAppId);
		attributeSetterBiConsumers.put(
			"appBuilderAppId",
			(BiConsumer<AppBuilderAppDeployment, Long>)
				AppBuilderAppDeployment::setAppBuilderAppId);
		attributeGetterFunctions.put(
			"deploymentType", AppBuilderAppDeployment::getDeploymentType);
		attributeSetterBiConsumers.put(
			"deploymentType",
			(BiConsumer<AppBuilderAppDeployment, String>)
				AppBuilderAppDeployment::setDeploymentType);
		attributeGetterFunctions.put(
			"settings", AppBuilderAppDeployment::getSettings);
		attributeSetterBiConsumers.put(
			"settings",
			(BiConsumer<AppBuilderAppDeployment, String>)
				AppBuilderAppDeployment::setSettings);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getAppBuilderAppDeploymentId() {
		return _appBuilderAppDeploymentId;
	}

	@Override
	public void setAppBuilderAppDeploymentId(long appBuilderAppDeploymentId) {
		_appBuilderAppDeploymentId = appBuilderAppDeploymentId;
	}

	@Override
	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		_columnBitmask |= APPBUILDERAPPID_COLUMN_BITMASK;

		if (!_setOriginalAppBuilderAppId) {
			_setOriginalAppBuilderAppId = true;

			_originalAppBuilderAppId = _appBuilderAppId;
		}

		_appBuilderAppId = appBuilderAppId;
	}

	public long getOriginalAppBuilderAppId() {
		return _originalAppBuilderAppId;
	}

	@Override
	public String getDeploymentType() {
		if (_deploymentType == null) {
			return "";
		}
		else {
			return _deploymentType;
		}
	}

	@Override
	public void setDeploymentType(String deploymentType) {
		_deploymentType = deploymentType;
	}

	@Override
	public String getSettings() {
		if (_settings == null) {
			return "";
		}
		else {
			return _settings;
		}
	}

	@Override
	public void setSettings(String settings) {
		_settings = settings;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			0, AppBuilderAppDeployment.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AppBuilderAppDeployment toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AppBuilderAppDeployment>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AppBuilderAppDeploymentImpl appBuilderAppDeploymentImpl =
			new AppBuilderAppDeploymentImpl();

		appBuilderAppDeploymentImpl.setAppBuilderAppDeploymentId(
			getAppBuilderAppDeploymentId());
		appBuilderAppDeploymentImpl.setAppBuilderAppId(getAppBuilderAppId());
		appBuilderAppDeploymentImpl.setDeploymentType(getDeploymentType());
		appBuilderAppDeploymentImpl.setSettings(getSettings());

		appBuilderAppDeploymentImpl.resetOriginalValues();

		return appBuilderAppDeploymentImpl;
	}

	@Override
	public int compareTo(AppBuilderAppDeployment appBuilderAppDeployment) {
		long primaryKey = appBuilderAppDeployment.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AppBuilderAppDeployment)) {
			return false;
		}

		AppBuilderAppDeployment appBuilderAppDeployment =
			(AppBuilderAppDeployment)obj;

		long primaryKey = appBuilderAppDeployment.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		AppBuilderAppDeploymentModelImpl appBuilderAppDeploymentModelImpl =
			this;

		appBuilderAppDeploymentModelImpl._originalAppBuilderAppId =
			appBuilderAppDeploymentModelImpl._appBuilderAppId;

		appBuilderAppDeploymentModelImpl._setOriginalAppBuilderAppId = false;

		appBuilderAppDeploymentModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AppBuilderAppDeployment> toCacheModel() {
		AppBuilderAppDeploymentCacheModel appBuilderAppDeploymentCacheModel =
			new AppBuilderAppDeploymentCacheModel();

		appBuilderAppDeploymentCacheModel.appBuilderAppDeploymentId =
			getAppBuilderAppDeploymentId();

		appBuilderAppDeploymentCacheModel.appBuilderAppId =
			getAppBuilderAppId();

		appBuilderAppDeploymentCacheModel.deploymentType = getDeploymentType();

		String deploymentType =
			appBuilderAppDeploymentCacheModel.deploymentType;

		if ((deploymentType != null) && (deploymentType.length() == 0)) {
			appBuilderAppDeploymentCacheModel.deploymentType = null;
		}

		appBuilderAppDeploymentCacheModel.settings = getSettings();

		String settings = appBuilderAppDeploymentCacheModel.settings;

		if ((settings != null) && (settings.length() == 0)) {
			appBuilderAppDeploymentCacheModel.settings = null;
		}

		return appBuilderAppDeploymentCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AppBuilderAppDeployment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AppBuilderAppDeployment, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AppBuilderAppDeployment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((AppBuilderAppDeployment)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<AppBuilderAppDeployment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AppBuilderAppDeployment, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AppBuilderAppDeployment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((AppBuilderAppDeployment)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, AppBuilderAppDeployment>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _appBuilderAppDeploymentId;
	private long _appBuilderAppId;
	private long _originalAppBuilderAppId;
	private boolean _setOriginalAppBuilderAppId;
	private String _deploymentType;
	private String _settings;
	private long _columnBitmask;
	private AppBuilderAppDeployment _escapedModel;

}