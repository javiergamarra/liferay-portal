/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.client.pagination.Pagination;
import com.liferay.search.experiences.rest.client.problem.Problem;

import java.util.Collections;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SearchResponseResourceTest
	extends BaseSearchResponseResourceTestCase {

	@Override
	@Test
	public void testGetSearch() throws Exception {
		searchResponseResource.getSearch(null, null, Pagination.of(1, 1));

		_testGetSearchWithBlueprint();
		_testGetSearchWithJSONIssue();
		_testGetSearchWithMultipleQueryIssues();
		_testGetSearchWithMultipleSchemaIssuesOnlyFirstIsReported();
		_testGetSearchWithSearchEngineIssue();
		_testGetSearchWithSearchEngineIssueInSearchResponseString();
	}

	private String _read(String name) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getResourceAsStream(
				StringBundler.concat(
					clazz.getSimpleName(), StringPool.PERIOD, name, ".json")));
	}

	private void _testGetSearchWithBlueprint() throws Exception {
		searchResponseResource.getSearch(
			null, _read("testGetSearchWithBlueprint"), null);
	}

	private void _testGetSearchWithJSONIssue() throws Exception {
		try {
			searchResponseResource.getSearch(
				null, "{ broken JSON syntax }", null);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString("Input is invalid JSON"));
		}
	}

	private void _testGetSearchWithMultipleQueryIssues() throws Exception {
		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

				searchResponseResource.getSearch(
					null, _read("testGetSearchWithMultipleQueryIssues"), null);

				Assert.fail();
			}
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.allOf(
					CoreMatchers.containsString("Invalid query entry at: 0"),
					CoreMatchers.containsString("value parameter is required"),
					CoreMatchers.containsString("Invalid query entry at: 1"),
					CoreMatchers.containsString(
						"Unresolved template variables: [ipstack.latitude, " +
							"ipstack.longitude]")));
		}
	}

	private void _testGetSearchWithMultipleSchemaIssuesOnlyFirstIsReported()
		throws Exception {

		try {
			searchResponseResource.getSearch(
				null,
				_read(
					"testGetSearchWithMultipleSchemaIssuesOnlyFirstIsReported"),
				null);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString(
					StringBundler.concat(
						"Property \"configuration\" is not defined in ",
						"SXPBlueprint.Property \"general\" is not defined in ",
						"Configuration.Property \"incorrectFirst\" is not ",
						"defined in General")));
		}
	}

	private void _testGetSearchWithSearchEngineIssue() throws Exception {
		try {
			try (ConfigurationTemporarySwapper configurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
						_CONFIGURATION_PID_ELASTICSEARCH,
						new HashMapDictionary<>(
							Collections.singletonMap(
								"logExceptionsOnly", false)))) {

				try (LogCapture logCapture =
						LoggerTestUtil.configureLog4JLogger(
							_CLASS_NAME_EXCEPTION_MAPPER,
							LoggerTestUtil.ERROR)) {

					searchResponseResource.getSearch(
						null, _read("testGetSearchWithSearchEngineIssue"),
						null);
				}
			}

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString(_SEARCH_ENGINE_ISSUE));
		}
	}

	private void _testGetSearchWithSearchEngineIssueInSearchResponseString()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID_ELASTICSEARCH,
					new HashMapDictionary<>(
						Collections.singletonMap("logExceptionsOnly", true)))) {

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER,
					LoggerTestUtil.ERROR)) {

				SearchResponse searchResponse =
					searchResponseResource.getSearch(
						null, _read("testGetSearchWithSearchEngineIssue"),
						null);

				Assert.assertNull(searchResponse.getResponse());

				Assert.assertThat(
					searchResponse.getResponseString(),
					CoreMatchers.containsString(_SEARCH_ENGINE_ISSUE));
			}
		}
	}

	private static final String _CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER =
		"com.liferay.portal.search.elasticsearch7.internal." +
			"ElasticsearchIndexSearcher";

	private static final String _CLASS_NAME_EXCEPTION_MAPPER =
		"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
			"ExceptionMapper";

	private static final String _CONFIGURATION_PID_ELASTICSEARCH =
		"com.liferay.portal.search.elasticsearch7.configuration." +
			"ElasticsearchConfiguration";

	private static final String _SEARCH_ENGINE_ISSUE = StringBundler.concat(
		"org.elasticsearch.ElasticsearchStatusException: ",
		"ElasticsearchStatusException[Elasticsearch exception ",
		"[type=parsing_exception, reason=[deliberately mistyped] query ",
		"malformed, no start_object after query name]]");

}