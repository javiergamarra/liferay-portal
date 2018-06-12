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

package com.liferay.knowledge.base.service.base;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateService;
import com.liferay.knowledge.base.service.persistence.KBArticleFinder;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.knowledge.base.service.persistence.KBCommentPersistence;
import com.liferay.knowledge.base.service.persistence.KBFolderFinder;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;
import com.liferay.knowledge.base.service.persistence.KBTemplatePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.social.kernel.service.persistence.SocialActivityPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the kb template remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.knowledge.base.service.impl.KBTemplateServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.knowledge.base.service.impl.KBTemplateServiceImpl
 * @see com.liferay.knowledge.base.service.KBTemplateServiceUtil
 * @generated
 */
public abstract class KBTemplateServiceBaseImpl extends BaseServiceImpl
	implements KBTemplateService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.knowledge.base.service.KBTemplateServiceUtil} to access the kb template remote service.
	 */

	/**
	 * Returns the kb article local service.
	 *
	 * @return the kb article local service
	 */
	public com.liferay.knowledge.base.service.KBArticleLocalService getKBArticleLocalService() {
		return kbArticleLocalService;
	}

	/**
	 * Sets the kb article local service.
	 *
	 * @param kbArticleLocalService the kb article local service
	 */
	public void setKBArticleLocalService(
		com.liferay.knowledge.base.service.KBArticleLocalService kbArticleLocalService) {
		this.kbArticleLocalService = kbArticleLocalService;
	}

	/**
	 * Returns the kb article remote service.
	 *
	 * @return the kb article remote service
	 */
	public com.liferay.knowledge.base.service.KBArticleService getKBArticleService() {
		return kbArticleService;
	}

	/**
	 * Sets the kb article remote service.
	 *
	 * @param kbArticleService the kb article remote service
	 */
	public void setKBArticleService(
		com.liferay.knowledge.base.service.KBArticleService kbArticleService) {
		this.kbArticleService = kbArticleService;
	}

	/**
	 * Returns the kb article persistence.
	 *
	 * @return the kb article persistence
	 */
	public KBArticlePersistence getKBArticlePersistence() {
		return kbArticlePersistence;
	}

	/**
	 * Sets the kb article persistence.
	 *
	 * @param kbArticlePersistence the kb article persistence
	 */
	public void setKBArticlePersistence(
		KBArticlePersistence kbArticlePersistence) {
		this.kbArticlePersistence = kbArticlePersistence;
	}

	/**
	 * Returns the kb article finder.
	 *
	 * @return the kb article finder
	 */
	public KBArticleFinder getKBArticleFinder() {
		return kbArticleFinder;
	}

	/**
	 * Sets the kb article finder.
	 *
	 * @param kbArticleFinder the kb article finder
	 */
	public void setKBArticleFinder(KBArticleFinder kbArticleFinder) {
		this.kbArticleFinder = kbArticleFinder;
	}

	/**
	 * Returns the kb comment local service.
	 *
	 * @return the kb comment local service
	 */
	public com.liferay.knowledge.base.service.KBCommentLocalService getKBCommentLocalService() {
		return kbCommentLocalService;
	}

	/**
	 * Sets the kb comment local service.
	 *
	 * @param kbCommentLocalService the kb comment local service
	 */
	public void setKBCommentLocalService(
		com.liferay.knowledge.base.service.KBCommentLocalService kbCommentLocalService) {
		this.kbCommentLocalService = kbCommentLocalService;
	}

	/**
	 * Returns the kb comment remote service.
	 *
	 * @return the kb comment remote service
	 */
	public com.liferay.knowledge.base.service.KBCommentService getKBCommentService() {
		return kbCommentService;
	}

	/**
	 * Sets the kb comment remote service.
	 *
	 * @param kbCommentService the kb comment remote service
	 */
	public void setKBCommentService(
		com.liferay.knowledge.base.service.KBCommentService kbCommentService) {
		this.kbCommentService = kbCommentService;
	}

	/**
	 * Returns the kb comment persistence.
	 *
	 * @return the kb comment persistence
	 */
	public KBCommentPersistence getKBCommentPersistence() {
		return kbCommentPersistence;
	}

	/**
	 * Sets the kb comment persistence.
	 *
	 * @param kbCommentPersistence the kb comment persistence
	 */
	public void setKBCommentPersistence(
		KBCommentPersistence kbCommentPersistence) {
		this.kbCommentPersistence = kbCommentPersistence;
	}

	/**
	 * Returns the kb folder local service.
	 *
	 * @return the kb folder local service
	 */
	public com.liferay.knowledge.base.service.KBFolderLocalService getKBFolderLocalService() {
		return kbFolderLocalService;
	}

	/**
	 * Sets the kb folder local service.
	 *
	 * @param kbFolderLocalService the kb folder local service
	 */
	public void setKBFolderLocalService(
		com.liferay.knowledge.base.service.KBFolderLocalService kbFolderLocalService) {
		this.kbFolderLocalService = kbFolderLocalService;
	}

	/**
	 * Returns the kb folder remote service.
	 *
	 * @return the kb folder remote service
	 */
	public com.liferay.knowledge.base.service.KBFolderService getKBFolderService() {
		return kbFolderService;
	}

	/**
	 * Sets the kb folder remote service.
	 *
	 * @param kbFolderService the kb folder remote service
	 */
	public void setKBFolderService(
		com.liferay.knowledge.base.service.KBFolderService kbFolderService) {
		this.kbFolderService = kbFolderService;
	}

	/**
	 * Returns the kb folder persistence.
	 *
	 * @return the kb folder persistence
	 */
	public KBFolderPersistence getKBFolderPersistence() {
		return kbFolderPersistence;
	}

	/**
	 * Sets the kb folder persistence.
	 *
	 * @param kbFolderPersistence the kb folder persistence
	 */
	public void setKBFolderPersistence(KBFolderPersistence kbFolderPersistence) {
		this.kbFolderPersistence = kbFolderPersistence;
	}

	/**
	 * Returns the kb folder finder.
	 *
	 * @return the kb folder finder
	 */
	public KBFolderFinder getKBFolderFinder() {
		return kbFolderFinder;
	}

	/**
	 * Sets the kb folder finder.
	 *
	 * @param kbFolderFinder the kb folder finder
	 */
	public void setKBFolderFinder(KBFolderFinder kbFolderFinder) {
		this.kbFolderFinder = kbFolderFinder;
	}

	/**
	 * Returns the kb template local service.
	 *
	 * @return the kb template local service
	 */
	public com.liferay.knowledge.base.service.KBTemplateLocalService getKBTemplateLocalService() {
		return kbTemplateLocalService;
	}

	/**
	 * Sets the kb template local service.
	 *
	 * @param kbTemplateLocalService the kb template local service
	 */
	public void setKBTemplateLocalService(
		com.liferay.knowledge.base.service.KBTemplateLocalService kbTemplateLocalService) {
		this.kbTemplateLocalService = kbTemplateLocalService;
	}

	/**
	 * Returns the kb template remote service.
	 *
	 * @return the kb template remote service
	 */
	public KBTemplateService getKBTemplateService() {
		return kbTemplateService;
	}

	/**
	 * Sets the kb template remote service.
	 *
	 * @param kbTemplateService the kb template remote service
	 */
	public void setKBTemplateService(KBTemplateService kbTemplateService) {
		this.kbTemplateService = kbTemplateService;
	}

	/**
	 * Returns the kb template persistence.
	 *
	 * @return the kb template persistence
	 */
	public KBTemplatePersistence getKBTemplatePersistence() {
		return kbTemplatePersistence;
	}

	/**
	 * Sets the kb template persistence.
	 *
	 * @param kbTemplatePersistence the kb template persistence
	 */
	public void setKBTemplatePersistence(
		KBTemplatePersistence kbTemplatePersistence) {
		this.kbTemplatePersistence = kbTemplatePersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the social activity local service.
	 *
	 * @return the social activity local service
	 */
	public com.liferay.social.kernel.service.SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	/**
	 * Sets the social activity local service.
	 *
	 * @param socialActivityLocalService the social activity local service
	 */
	public void setSocialActivityLocalService(
		com.liferay.social.kernel.service.SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	/**
	 * Returns the social activity remote service.
	 *
	 * @return the social activity remote service
	 */
	public com.liferay.social.kernel.service.SocialActivityService getSocialActivityService() {
		return socialActivityService;
	}

	/**
	 * Sets the social activity remote service.
	 *
	 * @param socialActivityService the social activity remote service
	 */
	public void setSocialActivityService(
		com.liferay.social.kernel.service.SocialActivityService socialActivityService) {
		this.socialActivityService = socialActivityService;
	}

	/**
	 * Returns the social activity persistence.
	 *
	 * @return the social activity persistence
	 */
	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	/**
	 * Sets the social activity persistence.
	 *
	 * @param socialActivityPersistence the social activity persistence
	 */
	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return KBTemplateService.class.getName();
	}

	protected Class<?> getModelClass() {
		return KBTemplate.class;
	}

	protected String getModelClassName() {
		return KBTemplate.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = kbTemplatePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.knowledge.base.service.KBArticleLocalService.class)
	protected com.liferay.knowledge.base.service.KBArticleLocalService kbArticleLocalService;
	@BeanReference(type = com.liferay.knowledge.base.service.KBArticleService.class)
	protected com.liferay.knowledge.base.service.KBArticleService kbArticleService;
	@BeanReference(type = KBArticlePersistence.class)
	protected KBArticlePersistence kbArticlePersistence;
	@BeanReference(type = KBArticleFinder.class)
	protected KBArticleFinder kbArticleFinder;
	@BeanReference(type = com.liferay.knowledge.base.service.KBCommentLocalService.class)
	protected com.liferay.knowledge.base.service.KBCommentLocalService kbCommentLocalService;
	@BeanReference(type = com.liferay.knowledge.base.service.KBCommentService.class)
	protected com.liferay.knowledge.base.service.KBCommentService kbCommentService;
	@BeanReference(type = KBCommentPersistence.class)
	protected KBCommentPersistence kbCommentPersistence;
	@BeanReference(type = com.liferay.knowledge.base.service.KBFolderLocalService.class)
	protected com.liferay.knowledge.base.service.KBFolderLocalService kbFolderLocalService;
	@BeanReference(type = com.liferay.knowledge.base.service.KBFolderService.class)
	protected com.liferay.knowledge.base.service.KBFolderService kbFolderService;
	@BeanReference(type = KBFolderPersistence.class)
	protected KBFolderPersistence kbFolderPersistence;
	@BeanReference(type = KBFolderFinder.class)
	protected KBFolderFinder kbFolderFinder;
	@BeanReference(type = com.liferay.knowledge.base.service.KBTemplateLocalService.class)
	protected com.liferay.knowledge.base.service.KBTemplateLocalService kbTemplateLocalService;
	@BeanReference(type = KBTemplateService.class)
	protected KBTemplateService kbTemplateService;
	@BeanReference(type = KBTemplatePersistence.class)
	protected KBTemplatePersistence kbTemplatePersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameService.class)
	protected com.liferay.portal.kernel.service.ClassNameService classNameService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.social.kernel.service.SocialActivityLocalService.class)
	protected com.liferay.social.kernel.service.SocialActivityLocalService socialActivityLocalService;
	@ServiceReference(type = com.liferay.social.kernel.service.SocialActivityService.class)
	protected com.liferay.social.kernel.service.SocialActivityService socialActivityService;
	@ServiceReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
}