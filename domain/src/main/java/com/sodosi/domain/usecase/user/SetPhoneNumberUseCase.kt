package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

/**
 *  SetPhoneNumberUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SetPhoneNumberUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(phoneNumber: String) {
        dataStoreRepository.setDataStoreString(KEY_PHONE_NUMBER, phoneNumber)
    }

    companion object {
        private const val KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER"
    }
}
