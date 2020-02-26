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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useState} from 'react';
import {withRouter} from 'react-router-dom';

import {getMessage, updateMessage} from '../../utils/client.es';
import {getCKEditorConfig, onBeforeLoadCKEditor} from '../../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {answerId}
		}
	}) => {
		const [articleBody, setArticleBody] = useState('');

		const loadMessage = () =>
			getMessage(answerId).then(({articleBody}) =>
				setArticleBody(articleBody)
			);

		const submit = () => {
			updateMessage(articleBody, answerId).then(() => history.goBack());
		};

		return (
			<section className="c-mt-5 c-mx-auto col-xl-10">
				<h1>{Liferay.Language.get('edit-answer')}</h1>

				<ClayForm>
					<ClayForm.Group className="c-mt-4">
						<label htmlFor="basicInput">
							{Liferay.Language.get('answer')}

							<span className="c-ml-2 reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<Editor
							config={getCKEditorConfig()}
							data={articleBody}
							onBeforeLoad={onBeforeLoadCKEditor}
							onChange={event =>
								setArticleBody(event.editor.getData())
							}
							onInstanceReady={loadMessage}
							required
							type="text"
						/>

						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<span className="small text-secondary">
									{Liferay.Language.get(
										'include-all-the-information-someone-would-need-to-answer-your-question'
									)}
								</span>
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					</ClayForm.Group>
				</ClayForm>

				<ClayButton.Group className="c-mt-4" spaced={true}>
					<ClayButton
						disabled={!articleBody}
						displayType="primary"
						onClick={submit}
					>
						{Liferay.Language.get('update-your-answer')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => history.goBack()}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</section>
		);
	}
);
