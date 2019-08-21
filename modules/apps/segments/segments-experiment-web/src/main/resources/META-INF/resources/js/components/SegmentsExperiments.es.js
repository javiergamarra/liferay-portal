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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClaySelect from '@clayui/select';
import Variants from './Variants/Variants.es';
import {
	InitialSegmentsVariantType,
	SegmentsExperienceType,
	SegmentsExperimentType
} from '../types.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';

function SegmentsExperiments({
	onCreateSegmentsExperiment,
	onEditSegmentsExperiment,
	onSelectSegmentsExperienceChange,
	onVariantCreation,
	onVariantDeletion,
	onVariantEdition,
	segmentsExperiences = [],
	segmentsExperiment,
	selectedSegmentsExperienceId,
	variants
}) {
	const [dropdown, setDropdown] = useState(false);

	const _selectedSegmentsExperienceId = segmentsExperiment
		? segmentsExperiment.segmentsExperienceId
		: selectedSegmentsExperienceId;

	return (
		<>
			{segmentsExperiences.length > 1 && (
				<div className="form-group">
					<label>{Liferay.Language.get('select-experience')}</label>
					<ClaySelect
						defaultValue={_selectedSegmentsExperienceId}
						onChange={_handleExperienceSelection}
					>
						{segmentsExperiences.map(segmentsExperience => {
							return (
								<ClaySelect.Option
									key={
										segmentsExperience.segmentsExperienceId
									}
									label={segmentsExperience.name}
									value={
										segmentsExperience.segmentsExperienceId
									}
								/>
							);
						})}
					</ClaySelect>
				</div>
			)}

			{segmentsExperiment && (
				<>
					<div className="d-flex justify-content-between align-items-center">
						<h3 className="mb-0 text-dark text-truncate">
							{segmentsExperiment.name}
						</h3>
						<ClayDropDown
							active={dropdown}
							onActiveChange={setDropdown}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get(
										'show-actions'
									)}
									borderless
									displayType="secondary"
									small={true}
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item
									onClick={_handleEditExperiment}
								>
									{Liferay.Language.get('edit')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</div>

					<ClayLabel
						displayType={_statusToType(
							segmentsExperiment.status.value
						)}
					>
						{segmentsExperiment.status.label}
					</ClayLabel>

					<SegmentsExperimentsDetails
						segmentsExperiment={segmentsExperiment}
					/>

					<Variants
						onVariantCreation={onVariantCreation}
						onVariantDeletion={onVariantDeletion}
						onVariantEdition={onVariantEdition}
						selectedSegmentsExperienceId={
							selectedSegmentsExperienceId
						}
						variants={variants}
					/>

					<ClayButton className="w-100" disabled>
						{Liferay.Language.get('review-and-run-test')}
					</ClayButton>
				</>
			)}
			{!segmentsExperiment && (
				<div className="text-center">
					<h4 className="text-dark">
						{Liferay.Language.get(
							'no-active-tests-were-found-for-the-selected-experience'
						)}
					</h4>
					<p className="small">
						{Liferay.Language.get('create-test-help-message')}
					</p>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							onCreateSegmentsExperiment(
								selectedSegmentsExperienceId
							)
						}
					>
						{Liferay.Language.get('create-test')}
					</ClayButton>
				</div>
			)}
		</>
	);

	function _handleExperienceSelection(event) {
		const segmentsExperienceId = event.target.value;

		onSelectSegmentsExperienceChange(segmentsExperienceId);
	}

	function _handleEditExperiment() {
		onEditSegmentsExperiment();
	}
}

const _statusToType = status => STATUS_TO_TYPE[status];

const STATUS_TO_TYPE = {
	0: 'secondary',
	1: 'primary',
	2: 'success',
	3: 'success',
	4: 'danger',
	5: 'danger',
	6: 'danger',
	7: 'warning'
};

SegmentsExperiments.propTypes = {
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onSelectSegmentsExperienceChange: PropTypes.func.isRequired,
	onVariantCreation: PropTypes.func.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	segmentsExperiment: SegmentsExperimentType,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	variants: PropTypes.arrayOf(InitialSegmentsVariantType)
};

export default SegmentsExperiments;
