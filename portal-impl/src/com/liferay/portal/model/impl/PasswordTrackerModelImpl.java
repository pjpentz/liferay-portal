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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.PasswordTracker;
import com.liferay.portal.kernel.model.PasswordTrackerModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the PasswordTracker service. Represents a row in the &quot;PasswordTracker&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link PasswordTrackerModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PasswordTrackerImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerImpl
 * @see PasswordTracker
 * @see PasswordTrackerModel
 * @generated
 */
@ProviderType
public class PasswordTrackerModelImpl extends BaseModelImpl<PasswordTracker>
	implements PasswordTrackerModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a password tracker model instance should use the {@link PasswordTracker} interface instead.
	 */
	public static final String TABLE_NAME = "PasswordTracker";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "passwordTrackerId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "password_", Types.VARCHAR }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("passwordTrackerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("password_", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE = "create table PasswordTracker (mvccVersion LONG default 0 not null,passwordTrackerId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,password_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table PasswordTracker";
	public static final String ORDER_BY_JPQL = " ORDER BY passwordTracker.userId DESC, passwordTracker.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY PasswordTracker.userId DESC, PasswordTracker.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.PasswordTracker"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.PasswordTracker"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.PasswordTracker"),
			true);
	public static final long USERID_COLUMN_BITMASK = 1L;
	public static final long CREATEDATE_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.kernel.model.PasswordTracker"));

	public PasswordTrackerModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _passwordTrackerId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPasswordTrackerId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _passwordTrackerId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return PasswordTracker.class;
	}

	@Override
	public String getModelClassName() {
		return PasswordTracker.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("passwordTrackerId", getPasswordTrackerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("password", getPassword());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long passwordTrackerId = (Long)attributes.get("passwordTrackerId");

		if (passwordTrackerId != null) {
			setPasswordTrackerId(passwordTrackerId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String password = (String)attributes.get("password");

		if (password != null) {
			setPassword(password);
		}
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getPasswordTrackerId() {
		return _passwordTrackerId;
	}

	@Override
	public void setPasswordTrackerId(long passwordTrackerId) {
		_passwordTrackerId = passwordTrackerId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask = -1L;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@Override
	public String getPassword() {
		if (_password == null) {
			return "";
		}
		else {
			return _password;
		}
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			PasswordTracker.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PasswordTracker toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (PasswordTracker)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		PasswordTrackerImpl passwordTrackerImpl = new PasswordTrackerImpl();

		passwordTrackerImpl.setMvccVersion(getMvccVersion());
		passwordTrackerImpl.setPasswordTrackerId(getPasswordTrackerId());
		passwordTrackerImpl.setCompanyId(getCompanyId());
		passwordTrackerImpl.setUserId(getUserId());
		passwordTrackerImpl.setCreateDate(getCreateDate());
		passwordTrackerImpl.setPassword(getPassword());

		passwordTrackerImpl.resetOriginalValues();

		return passwordTrackerImpl;
	}

	@Override
	public int compareTo(PasswordTracker passwordTracker) {
		int value = 0;

		if (getUserId() < passwordTracker.getUserId()) {
			value = -1;
		}
		else if (getUserId() > passwordTracker.getUserId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(),
				passwordTracker.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PasswordTracker)) {
			return false;
		}

		PasswordTracker passwordTracker = (PasswordTracker)obj;

		long primaryKey = passwordTracker.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		PasswordTrackerModelImpl passwordTrackerModelImpl = this;

		passwordTrackerModelImpl._originalUserId = passwordTrackerModelImpl._userId;

		passwordTrackerModelImpl._setOriginalUserId = false;

		passwordTrackerModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PasswordTracker> toCacheModel() {
		PasswordTrackerCacheModel passwordTrackerCacheModel = new PasswordTrackerCacheModel();

		passwordTrackerCacheModel.mvccVersion = getMvccVersion();

		passwordTrackerCacheModel.passwordTrackerId = getPasswordTrackerId();

		passwordTrackerCacheModel.companyId = getCompanyId();

		passwordTrackerCacheModel.userId = getUserId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			passwordTrackerCacheModel.createDate = createDate.getTime();
		}
		else {
			passwordTrackerCacheModel.createDate = Long.MIN_VALUE;
		}

		passwordTrackerCacheModel.password = getPassword();

		String password = passwordTrackerCacheModel.password;

		if ((password != null) && (password.length() == 0)) {
			passwordTrackerCacheModel.password = null;
		}

		return passwordTrackerCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", passwordTrackerId=");
		sb.append(getPasswordTrackerId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", password=");
		sb.append(getPassword());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.kernel.model.PasswordTracker");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passwordTrackerId</column-name><column-value><![CDATA[");
		sb.append(getPasswordTrackerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>password</column-name><column-value><![CDATA[");
		sb.append(getPassword());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = PasswordTracker.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			PasswordTracker.class, ModelWrapper.class
		};
	private long _mvccVersion;
	private long _passwordTrackerId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private String _password;
	private long _columnBitmask;
	private PasswordTracker _escapedModel;
}