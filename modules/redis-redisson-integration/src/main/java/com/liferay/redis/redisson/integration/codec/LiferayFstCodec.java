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

package com.liferay.redis.redisson.integration.codec;

import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import org.nustaq.serialization.FSTConfiguration;

import org.redisson.codec.FstCodec;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LiferayFstCodec extends FstCodec {

	public LiferayFstCodec() {
		super(PortalClassLoaderUtil.getClassLoader());
	}

	public LiferayFstCodec(ClassLoader classLoader) {
		super(
			AggregateClassLoader.getAggregateClassLoader(
				PortalClassLoaderUtil.getClassLoader(), classLoader));
	}

	public LiferayFstCodec(ClassLoader classLoader, LiferayFstCodec codec) {
		super(
			AggregateClassLoader.getAggregateClassLoader(
				PortalClassLoaderUtil.getClassLoader(), classLoader),
			codec);
	}

	public LiferayFstCodec(FSTConfiguration fstConfiguration) {
		throw new UnsupportedOperationException();
	}

	public LiferayFstCodec(
		FSTConfiguration fstConfiguration, boolean useCache) {

		throw new UnsupportedOperationException();
	}

}