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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class FacetsAssert {

	public static void assertFrequencies(
		String facetName, SearchContext searchContext, List<String> expected) {

		Facet facet = searchContext.getFacet(facetName);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		Assert.assertNotNull(termCollectors);

		Assert.assertEquals(
			(String)searchContext.getAttribute("queryString"),
			expected.toString(),
			_toString(
				termCollectors,
				termCollector ->
					termCollector.getTerm() + "=" +
						termCollector.getFrequency()));
	}

	private static <T> String _toString(
		List<? extends T> list, Function<? super T, String> function) {

		Stream<? extends T> stream = list.stream();

		return stream.map(
			function
		).collect(
			Collectors.toList()
		).toString();
	}

}