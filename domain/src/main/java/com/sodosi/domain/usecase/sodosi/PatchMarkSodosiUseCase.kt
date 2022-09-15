package com.sodosi.domain.usecase.sodosi

import com.sodosi.domain.Result
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  PatchMarkSodosiUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/15
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PatchMarkSodosiUseCase @Inject constructor(
    private val sodosiRepository: SodosiRepository,
) {
    suspend operator fun invoke(id: Long, markState: Boolean): Result<Boolean> {
        return if (markState) {
            sodosiRepository.unmarkSodosi(id)
        } else {
            sodosiRepository.markSodosi(id)
        }
    }
}
