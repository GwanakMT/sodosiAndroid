package com.sodosi.domain.usecase.sodosi

import com.sodosi.domain.Result
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  ReportSodosiUseCase.kt
 *
 *  Created by Minji Jeong on 2023/01/03
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ReportSodosiUseCase @Inject constructor(
    private val sodosiRepository: SodosiRepository,
) {
    suspend operator fun invoke(sodosiId: Long, reason: String): Result<Unit> {
        return sodosiRepository.reportSodosi(sodosiId, reason)
    }
}
