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
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.test.rule.Inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Víctor Galán
 */
public class BaseBlogPostingActionRouterTest {

	protected BlogPosting addBlogPosting(
			long groupId, BlogPosting blogPosting, User user)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"createBlogPosting", long.class, BlogPosting.class,
			CurrentUser.class);

		method.setAccessible(true);

		try {
			return (BlogPosting)method.invoke(
				_actionRouter, groupId, blogPosting, new CurrentUser(user));
		}
		catch (InvocationTargetException ite) {
			throw (Exception)ite.getTargetException();
		}
	}

	protected PageItems<BlogPosting> getPageItems(
			Pagination pagination, long groupId)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(_actionRouter, pagination, groupId);
	}

	protected BlogPosting replaceBlogPosting(
			long blogEntryId, BlogPosting blogPosting, User currentUser)
		throws Exception {

		Class<? extends ActionRouter> clazz = _actionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"replaceBlogPosting", long.class, BlogPosting.class,
			CurrentUser.class);

		method.setAccessible(true);

		return (BlogPosting)method.invoke(
			_actionRouter, blogEntryId, blogPosting, new CurrentUser(currentUser));
	}

	@Inject(
		filter = "component.name=com.liferay.blog.apio.internal.architect.resource.BlogPostingActionRouter"
	)
	private ActionRouter _actionRouter;

}