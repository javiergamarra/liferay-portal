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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectHandler;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;

import graphql.schema.GraphQLSchema;

import graphql.servlet.SimpleGraphQLHttpServlet;

import java.util.Dictionary;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		GraphQLFieldRetriever graphQLFieldRetriever =
			new GraphQLFieldRetriever();
		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever();

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever() {
			{
				setExtensionsHandler(
					new GraphQLExtensionsHandler() {
						{
							setFieldRetriever(graphQLFieldRetriever);
							setFieldSearchAlgorithm(parentalSearch);
							setGraphQLObjectInfoRetriever(
								graphQLObjectInfoRetriever);
							setMethodSearchAlgorithm(breadthFirstSearch);
						}
					});
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(graphQLFieldRetriever);
				setGraphQLInterfaceRetriever(graphQLInterfaceRetriever);
				setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
				setMethodSearchAlgorithm(breadthFirstSearch);
			}
		};

		// Handle Circular reference between GraphQLInterfaceRetriever and
		// GraphQLTypeRetriever

		graphQLInterfaceRetriever.setGraphQLTypeRetriever(graphQLTypeRetriever);

		_defaultTypeFunction = new DefaultTypeFunction(
			new GraphQLInputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			},
			new GraphQLOutputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			});
		_graphQLObjectHandler = new GraphQLObjectHandler() {
			{
				setTypeRetriever(graphQLTypeRetriever);
			}
		};

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private DefaultTypeFunction _defaultTypeFunction;
	private GraphQLObjectHandler _graphQLObjectHandler;
	private ServiceTracker<?, ?> _serviceTracker;

	private static class Tracked {

		public Tracked(
			ServiceRegistration<ServletContextHelper>
				servletContextHelperServiceRegistration,
			ServiceRegistration<Servlet> servletServiceRegistration) {

			_servletContextHelperServiceRegistration =
				servletContextHelperServiceRegistration;
			_servletServiceRegistration = servletServiceRegistration;
		}

		private final ServiceRegistration<ServletContextHelper>
			_servletContextHelperServiceRegistration;
		private final ServiceRegistration<Servlet> _servletServiceRegistration;

	}

	private class ServletDataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServletData, Tracked> {

		@Override
		public Tracked addingService(
			ServiceReference<ServletData> serviceReference) {

			// Schema

			GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

			ServletData servletData = _bundleContext.getService(
				serviceReference);

			Object mutation = servletData.getMutation();

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			schemaBuilder.mutation(
				_graphQLObjectHandler.getObject(
					mutation.getClass(), processingElementsContainer));

			Object query = servletData.getQuery();

			schemaBuilder.query(
				_graphQLObjectHandler.getObject(
					query.getClass(), processingElementsContainer));

			// Servlet

			SimpleGraphQLHttpServlet.Builder servletBuilder =
				SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

			Servlet servlet = servletBuilder.build();

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			String path = servletData.getPath();

			Class<? extends ServletData> clazz = servletData.getClass();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
				clazz.getName());
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, path);
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET,
				"GraphQL");

			ServiceRegistration<ServletContextHelper>
				servletContextHelperServiceRegistration =
					_bundleContext.registerService(
						ServletContextHelper.class,
						new ServletContextHelper() {
						},
						properties);

			properties = new HashMapDictionary<>();

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				StringBundler.concat(
					"(osgi.http.whiteboard.context.name=", clazz.getName(),
					")"));
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				"GraphQL");
			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

			ServiceRegistration<Servlet> servletServiceRegistration =
				_bundleContext.registerService(
					Servlet.class, servlet, properties);

			return new Tracked(
				servletContextHelperServiceRegistration,
				servletServiceRegistration);
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletData> serviceReference, Tracked tracked) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletData> serviceReference, Tracked tracked) {

			tracked._servletContextHelperServiceRegistration.unregister();
			tracked._servletServiceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private ServletDataServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}