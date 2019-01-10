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

package com.liferay.blog.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.Inject;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Víctor Galán
 */
public class BaseBlogPostingNestedCollectionResourceTest {

	protected BlogsEntry addBlogsEntry(
			long groupId, BlogPosting blogPosting, User user)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"addBlogsEntry", long.class, BlogPosting.class, CurrentUser.class);

		method.setAccessible(true);

		try {
			return (BlogsEntry)method.invoke(
				clazz, groupId, blogPosting, new CurrentUser(user));
		}
		catch (InvocationTargetException ite) {
			throw (Exception)ite.getTargetException();
		}
	}

	protected PageItems<BlogsEntry> getPageItems(
			Pagination pagination, long groupId)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(clazz, pagination, groupId);
	}

	protected BlogsEntry updateBlogsEntry(
			long blogEntryId, BlogPosting blogPosting, User currentUser)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"updateBlogsEntry", long.class, BlogPosting.class,
			CurrentUser.class);

		method.setAccessible(true);

		return (BlogsEntry)method.invoke(
			clazz, blogEntryId, blogPosting, new CurrentUser(currentUser));
	}

	@Inject(
		filter = "component.name=com.liferay.blog.apio.internal.architect.resource.BlogPostingActionRouter"
	)
	private ActionRouter _actionRouter;

}