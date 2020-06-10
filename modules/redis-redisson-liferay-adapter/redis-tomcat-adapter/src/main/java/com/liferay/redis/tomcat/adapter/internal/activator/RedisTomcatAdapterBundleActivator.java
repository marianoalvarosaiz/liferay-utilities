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

package com.liferay.redis.tomcat.adapter.internal.activator;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.redis.tomcat.client.constants.RedissonConstants;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Mariano Álvaro Sáiz
 */
public class RedisTomcatAdapterBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoaderPool.register(
			RedissonConstants.REDIS_CLASSLOADER_NAME,
			bundleWiring.getClassLoader());
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		ClassLoaderPool.unregister(RedissonConstants.REDIS_CLASSLOADER_NAME);
	}

}