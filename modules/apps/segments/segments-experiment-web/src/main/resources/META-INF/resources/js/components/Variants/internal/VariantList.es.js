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

import React from 'react';
import PropTypes from 'prop-types';
import ClayList from '@clayui/list';
import Variant from './Variant.es';
import {SegmentsVariantType} from '../../../types.es';

function VariantList({
	onVariantDeletion,
	onVariantEdition,
	selectedSegmentsExperienceId,
	variants
}) {
	return (
		<ClayList>
			{variants.map(variant => {
				return (
					<Variant
						active={
							variant.segmentsExperienceId ===
							selectedSegmentsExperienceId
						}
						control={variant.control}
						key={variant.segmentsExperimentRelId}
						name={variant.name}
						onVariantDeletion={onVariantDeletion}
						onVariantEdition={onVariantEdition}
						segmentsExperienceId={variant.segmentsExperienceId}
						variantId={variant.segmentsExperimentRelId}
					/>
				);
			})}
		</ClayList>
	);
}

VariantList.propTypes = {
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export default VariantList;
