package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  SetLastVisitedTimeUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/06
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SetLastVisitedTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(currentTimeMillis: Long) {
        val lastVisitedTime = dataStoreRepository.getDataStoreLongOnce(KEY_CURRENT_TIME) ?: 0
        dataStoreRepository.setDataStoreLong(KEY_CURRENT_TIME, currentTimeMillis)
        dataStoreRepository.setDataStoreLong(KEY_LAST_VISITED_TIME, lastVisitedTime)
    }

    companion object {
        private const val KEY_CURRENT_TIME = "KEY_CURRENT_TIME"
        private const val KEY_LAST_VISITED_TIME = "KEY_LAST_VISITED_TIME"
    }
}
