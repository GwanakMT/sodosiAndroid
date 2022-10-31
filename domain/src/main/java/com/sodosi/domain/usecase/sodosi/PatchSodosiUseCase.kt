package com.sodosi.domain.usecase.sodosi

import com.sodosi.domain.Result
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  PatchSodosiUseCase.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PatchSodosiUseCase @Inject constructor(
    private val sodosiRepository: SodosiRepository,
) {
    suspend operator fun invoke(id: Long, name: String, icon: String, viewState: Boolean): Result<Long> {
        return sodosiRepository.patchSodosi(id, name, icon, viewState)
    }
}
