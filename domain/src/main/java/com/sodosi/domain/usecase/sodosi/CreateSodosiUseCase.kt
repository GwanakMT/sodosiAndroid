package com.sodosi.domain.usecase.sodosi

import com.sodosi.domain.Result
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  CreateSodosiUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CreateSodosiUseCase @Inject constructor(
    private val sodosiRepository: SodosiRepository
) {
    suspend operator fun invoke(
        name: String,
        icon: String,
        viewState: Boolean
    ): Result<Long> {
        return sodosiRepository.createSodosi(name, icon, viewState)
    }
}
