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

package com.liferay.portal.vulcan.internal.batch;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.VulcanBatchEngineTaskItemDelegate;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class VulcanBatchEngineTaskItemDelegateAdaptorFactory {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, VulcanBatchEngineTaskItemDelegate.class,
			new VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
				bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<?, ?> _serviceTracker;

	private static class
		VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
			implements ServiceTrackerCustomizer
				<VulcanBatchEngineTaskItemDelegate, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference) {

			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate = _bundleContext.getService(
					serviceReference);

			VulcanBatchEngineTaskItemDelegateAdaptor<?>
				vulcanBatchEngineTaskItemDelegateAdaptor =
					new VulcanBatchEngineTaskItemDelegateAdaptor<>(
						vulcanBatchEngineTaskItemDelegate);

			return _bundleContext.registerService(
				BatchEngineTaskItemDelegate.class,
				vulcanBatchEngineTaskItemDelegateAdaptor, null);
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}