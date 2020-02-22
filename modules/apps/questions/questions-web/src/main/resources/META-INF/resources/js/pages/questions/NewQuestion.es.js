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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import {createQuestion} from '../../utils/client.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../../utils/utils.es';

export default withRouter(({history}) => {
	const context = useContext(AppContext);
	const [articleBody, setArticleBody] = useState('');
	const [headline, setHeadline] = useState('');
	const [tags, setTags] = useState('');

	const submit = () =>
		createQuestion(articleBody, headline, tags, context.siteKey).then(() =>
			history.push('/')
		);

	return (
		<>
			<h1>{Liferay.Language.get('new-question')}</h1>

			<ClayForm>
				<ClayForm.Group className="form-group-sm">
					<label htmlFor="basicInput">
						{Liferay.Language.get('title')}
						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>
					<ClayInput
						onChange={event => setHeadline(event.target.value)}
						placeholder={Liferay.Language.get(
							'what-is-your-question'
						)}
						required
						type="text"
						value={headline}
					/>
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{Liferay.Language.get(
								'be-specific-and-imagine-you-are-asking-a-question-to-another-person'
							)}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				</ClayForm.Group>
				<ClayForm.Group className="form-group-sm">
					<label htmlFor="basicInput">
						{Liferay.Language.get('body')}
						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<Editor
						config={getCKEditorConfig()}
						onBeforeLoad={onBeforeLoadCKEditor}
						onChange={event =>
							setArticleBody(event.editor.getData())
						}
						required
						value={articleBody}
					/>

					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{Liferay.Language.get(
								'include-all-the-information-someone-would-need-to-answer-your-question'
							)}
						</ClayForm.FeedbackItem>
						<ClayForm.Text>{''}</ClayForm.Text>
					</ClayForm.FeedbackGroup>
				</ClayForm.Group>
				<ClayForm.Group className="form-group-sm">
					<label htmlFor="basicInput">
						{Liferay.Language.get('tags')}
					</label>
					<ClayInput
						onChange={event => setTags(event.target.value)}
						placeholder={Liferay.Language.get('add-your-tags')}
						type="text"
						value={tags}
					/>
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							{Liferay.Language.get(
								'add-up-to-5-tags-to-describe-what-your-question-is-about'
							)}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				</ClayForm.Group>
			</ClayForm>

			<div className="sheet-footer">
				<div className="btn-group-item">
					<div className="btn-group-item">
						<button
							className="btn btn-primary"
							disabled={!articleBody || !headline}
							onClick={submit}
						>
							{Liferay.Language.get('post-your-question')}
						</button>
					</div>
					<div className="btn-group-item">
						<Link to={`/`}>
							<button className="btn btn-secondary">
								{Liferay.Language.get('cancel')}
							</button>
						</Link>
					</div>
				</div>
			</div>
		</>
	);
});
