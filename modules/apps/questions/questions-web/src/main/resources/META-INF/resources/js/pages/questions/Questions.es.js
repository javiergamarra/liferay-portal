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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Error from '../../components/Error.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import SectionLabel from '../../components/SectionLabel.es';
import TagList from '../../components/TagList.es';
import UserIcon from '../../components/UserIcon.es';
import {getRankedThreads, getThreads} from '../../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../../utils/utils.es';
import QuestionsNavigationBar from '../QuestionsNavigationBar.es';
import QuestionRow from '../../components/QuestionRow.es';

export default ({
	match: {
		params: {creatorId, sectionTitle, tag: taxonomyCategoryId},
	},
}) => {
	const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(20);
	const [questions, setQuestions] = useState([]);
	const [search, setSearch] = useState('');
	const [sectionId, setSectionId] = useState();

	useEffect(() => {
		if (sectionId) {
			renderQuestions(loadThreads());
		}
	}, [
		creatorId,
		page,
		pageSize,
		sectionId,
		search,
		taxonomyCategoryId,
		loadThreads,
	]);

	const renderQuestions = questions => {
		questions
			.then(data => setQuestions(data || []))
			.then(() => setLoading(false))
			.catch(error => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
				setLoading(false);
				setError({message: 'Loading Questions', title: 'Error'});
			});
	};

	const loadThreads = useCallback(
		sort =>
			getThreads({
				creatorId,
				page,
				pageSize,
				search,
				sectionId,
				sort,
				taxonomyCategoryId,
			}),
		[creatorId, page, pageSize, search, sectionId, taxonomyCategoryId]
	);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	const filterChange = type => {
		if (type === 'latest-edited') {
			renderQuestions(loadThreads('dateModified:desc'));
		}
		else if (type === 'week') {
			const date = new Date();
			date.setDate(date.getDate() - 7);

			renderQuestions(getRankedThreads(date, page, pageSize, sectionId));
		}
		else if (type === 'month') {
			const date = new Date();
			date.setDate(date.getDate() - 31);

			renderQuestions(getRankedThreads(date, page, pageSize, sectionId));
		}
		else {
			renderQuestions(loadThreads('dateCreated:desc'));
		}
	};

	return (
		<section className="c-mt-5 c-mx-auto c-px-0 col-xl-10">
			<QuestionsNavigationBar
				filterChange={filterChange}
				searchChange={search => setSearch(search)}
				sectionChange={section => setSectionId(section.id)}
			/>

			{loading ? (
				<ClayLoadingIndicator />
			) : (
				questions.items &&
				questions.items.map(question => (
					<QuestionRow question={question} />
				))
			)}

			{!!questions.totalCount &&
				questions.totalCount > questions.pageSize && (
					<ClayPaginationWithBasicItems
						activePage={page}
						ellipsisBuffer={2}
						onPageChange={setPage}
						totalPages={Math.ceil(
							questions.totalCount / questions.pageSize
						)}
					/>
				)}
			<Error error={error} />
		</section>
	);
};
