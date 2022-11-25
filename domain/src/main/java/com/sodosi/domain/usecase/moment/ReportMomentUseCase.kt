package com.sodosi.domain.usecase.moment

import com.sodosi.domain.Result
import com.sodosi.domain.repository.MomentRepository
import javax.inject.Inject

/**
 *  ReportMomentUseCase.kt
 *
 *  Created by Minji Jeong on 2022/11/26
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ReportMomentUseCase @Inject constructor(
    private val momentRepository: MomentRepository,
) {
    suspend operator fun invoke(sodosiId: Long, momentId: Long, reason: String): Result<Unit> {
        return momentRepository.reportMoment(sodosiId, momentId, reason)
    }
}
