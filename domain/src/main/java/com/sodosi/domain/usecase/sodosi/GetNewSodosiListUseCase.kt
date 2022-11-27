package com.sodosi.domain.usecase.sodosi

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  GetNewSodosiListUseCase.kt
 *
 *  Created by Minji Jeong on 2022/11/27
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetNewSodosiListUseCase @Inject constructor(
    private val sodosiRepository: SodosiRepository,
) {
    suspend operator fun invoke(): Result<List<Sodosi>> {
        return sodosiRepository.getNewSodosiList()
    }
}