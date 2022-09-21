package com.sodosi.domain.usecase.home

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  SetMainSuggestBannerHiddenUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/22
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SetMainSuggestBannerHiddenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.setSuggestBannerHidden()
    }
}
