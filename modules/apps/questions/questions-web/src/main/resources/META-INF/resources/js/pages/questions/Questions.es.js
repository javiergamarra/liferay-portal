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
import React, {useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import TagList from '../../components/TagList.es';
import UserIcon from '../../components/UserIcon.es';
import {getThreads} from '../../utils/client.es';
import {dateToInternationalHuman} from '../../utils/utils.es';

export default ({
	match: {
		params: {creatorId, tag}
	}
}) => {
	const context = useContext(AppContext);

	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(5);
	const [questions, setQuestions] = useState([]);

	useEffect(() => {
		getThreads({
			creatorId,
			page,
			pageSize,
			siteKey: context.siteKey,
			tag
		})
			.then(data => setQuestions(data))
			.then(() => setLoading(false));
	}, [creatorId, page, pageSize, context.siteKey, tag]);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	return (
		<section className="c-mt-5 c-mx-auto col-xl-10">
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				questions.items &&
				questions.items.map(question => (
					<div
						className={'c-mt-4 c-p-3 question-row'}
						key={question.id}
					>
						<div className="autofit-padded-no-gutter autofit-row">
							<div className="autofit-col autofit-col-expand">
								{/* {question.category} */}
							</div>

							<div className="autofit-col">
								<ul className="question-list">
									<li>
										<QuestionBadge
											symbol="caret-top"
											value={
												question.aggregateRating &&
												question.aggregateRating
													.ratingCount
											}
										/>
									</li>

									<li>
										<QuestionBadge
											symbol="view"
											value={question.viewCount}
										/>
									</li>

									<li>
										<QuestionBadge
											className={
												hasValidAnswer(question)
													? 'question-badge-success'
													: ''
											}
											symbol={
												hasValidAnswer(question)
													? 'check-circle-full'
													: 'message'
											}
											value={
												question.messageBoardMessages
													.items.length
											}
										/>
									</li>
								</ul>
							</div>
						</div>

						<Link
							className="question-title stretched-link"
							to={'/questions/' + question.id}
						>
							<h2 className="c-mb-0 stretched-link-layer">
								{question.headline}
							</h2>
						</Link>

						<p className="c-mb-0 c-mt-3 stretched-link-layer text-truncate">
							<ArticleBodyRenderer {...question} />
						</p>

						<div className="autofit-padded-no-gutters autofit-row autofit-row-center c-mt-3">
							<div className="autofit-col autofit-col-expand">
								<div className="autofit-row autofit-row-center">
									<Link
										className="question-user stretched-link-layer"
										to={
											'/questions/creator/' +
											question.creator.id
										}
									>
										<UserIcon
											fullName={question.creator.name}
											portraitURL={question.creator.image}
											size="sm"
											userId={String(question.creator.id)}
										/>

										<strong className="c-ml-2">
											{question.creator.name}
										</strong>
									</Link>

									<span className="c-ml-2 stretched-link-layer">
										{'- ' +
											dateToInternationalHuman(
												question.dateModified
											)}
									</span>
								</div>
							</div>

							<div className="autofit-col">
								<TagList tags={question.keywords} />
							</div>
						</div>
					</div>
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
		</section>
	);
};
