package com.sodosi.domain.usecase.moment

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Place
import com.sodosi.domain.repository.MomentRepository
import javax.inject.Inject

/**
 *  GetPlaceListBySodosi.kt
 *
 *  Created by Minji Jeong on 2022/11/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetPlaceListBySodosi @Inject constructor(
    private val momentRepository: MomentRepository
) {
    suspend operator fun invoke(sodosiId: Long): Result<List<Place>> {
        return momentRepository.getPlaceListBySodosi(sodosiId)
    }
}
