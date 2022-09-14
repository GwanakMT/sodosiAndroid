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
        dataStoreRepository.setLastVisitedTime(currentTimeMillis)
    }
}
