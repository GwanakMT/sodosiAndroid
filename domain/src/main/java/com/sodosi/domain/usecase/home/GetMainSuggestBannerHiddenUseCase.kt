package com.sodosi.domain.usecase.home

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  GetMainSuggestBannerHiddenUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/22
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetMainSuggestBannerHiddenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): Boolean {
        return dataStoreRepository.getSuggestBannerHidden()
    }
}
