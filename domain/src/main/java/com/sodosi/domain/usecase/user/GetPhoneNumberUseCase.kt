package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  GetPhoneNumberUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetPhoneNumberUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(): String {
        return dataStoreRepository.getDataStoreStringOnce(KEY_PHONE_NUMBER) ?: ""
    }

    companion object {
        private const val KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER"
    }
}
