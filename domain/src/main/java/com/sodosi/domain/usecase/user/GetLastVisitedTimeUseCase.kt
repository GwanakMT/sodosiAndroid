package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  GetLastVisitedTimeUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/06
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetLastVisitedTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(currentTimeMillis: Long): String {
        val diffTime = dataStoreRepository.getDataStoreLongOnce(KEY_LAST_VISITED_TIME)?.let {
            (currentTimeMillis - it) / 1000
        } ?: 0

        return if (diffTime < SEC) {
            "방금 전"
        } else if ((diffTime / SEC) < MIN) {
            "${(diffTime / SEC)}분 전"
        } else if ((diffTime / SEC / MIN) < HOUR) {
            "${(diffTime / SEC / MIN)}시간 전"
        } else if ((diffTime / SEC / MIN / HOUR) < DAY) {
            "${(diffTime / SEC / MIN / HOUR)}일 전"
        } else if ((diffTime / SEC / MIN / HOUR / DAY) < MONTH) {
            "${(diffTime / SEC / MIN / HOUR / DAY)}달 전"
        } else {
            "${(diffTime / SEC / MIN / HOUR / DAY / MONTH)}년 전"
        }
    }

    companion object {
        private const val KEY_LAST_VISITED_TIME = "KEY_LAST_VISITED_TIME"

        private const val SEC = 60
        private const val MIN = 60
        private const val HOUR = 24
        private const val DAY = 30
        private const val MONTH = 12
    }
}
