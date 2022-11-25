package com.sodosi.domain.usecase.moment

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.repository.MomentRepository
import javax.inject.Inject

/**
 *  GetMyMomentListUseCase.kt
 *
 *  Created by Minji Jeong on 2022/11/26
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetMyMomentListUseCase @Inject constructor(
    private val momentRepository: MomentRepository,
) {
    suspend operator fun invoke(): Result<List<Moment>> {
        return momentRepository.getMyMomentList()
    }
}
