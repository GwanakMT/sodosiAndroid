package com.sodosi.domain.usecase.home

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.entity.SodosiCategory
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  GetMainSodosiListUseCase.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetMainSodosiListUseCase @Inject constructor(
    private val repository: SodosiRepository
) {
    suspend operator fun invoke(): Result<Pair<Boolean, Map<SodosiCategory, List<Sodosi>>>> {
        return repository.getMainSodosiList()
    }
}
