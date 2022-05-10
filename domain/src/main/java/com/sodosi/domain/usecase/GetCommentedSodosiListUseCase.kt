package com.sodosi.domain.usecase

import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.repository.SodosiListRepository
import javax.inject.Inject

/**
 *  GetCommentedSodosiListUseCase.kt
 *
 *  Created by Minji Jeong on 2022/04/01
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetCommentedSodosiListUseCase @Inject constructor(
    private val repository: SodosiListRepository
) {
    suspend operator fun invoke(): List<Sodosi> {
        return repository.getCommentedSodosiList()
    }
}
