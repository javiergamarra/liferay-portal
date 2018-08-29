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

package com.liferay.structured.content.apio.internal.architect.filter;

import org.apache.olingo.server.api.uri.queryoption.expression.Binary;
import org.apache.olingo.server.api.uri.queryoption.expression.BinaryOperatorKind;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David Arques
 */
public class FilterParserTest {

	@Before
	public void setUp() {
		_filterParser = new FilterParserImpl();

		_filterParser.activate();
	}

	@Test
	public void testParseNonexistingField() {
		String filter = "(nonExistingField eq 'value')";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParser.parse(filter)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from filter '%s': 'Unknown property.'",
				filter));
	}

	@Test
	public void testParseWithEmptyFilter() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParser.parse("")
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Filter is empty");
	}

	@Test
	public void testParseWithNoSingleQuotes() {
		String filter = "(title eq title1)";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParser.parse(filter)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from filter '%s': 'Unknown property.'",
				filter));
	}

	@Test
	public void testParseWithNullFilter() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParser.parse(null)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Filter is empty");
	}

	@Test
	public void testParseWithSingleQuotes() {
		Expression expression = _filterParser.parse("title eq 'title1'");

		Assert.assertNotNull(expression);

		Binary binary = (Binary)expression;

		Assert.assertEquals(BinaryOperatorKind.EQ, binary.getOperator());
		Assert.assertEquals("[title]", binary.getLeftOperand().toString());
		Assert.assertEquals("'title1'", binary.getRightOperand().toString());
	}

	@Test
	public void testParseWithSingleQuotesAndParentheses() {
		Expression expression = _filterParser.parse("(title eq 'title1')");

		Assert.assertNotNull(expression);

		Binary binary = (Binary)expression;

		Assert.assertEquals(BinaryOperatorKind.EQ, binary.getOperator());
		Assert.assertEquals("[title]", binary.getLeftOperand().toString());
		Assert.assertEquals("'title1'", binary.getRightOperand().toString());
	}

	private FilterParserImpl _filterParser;

}