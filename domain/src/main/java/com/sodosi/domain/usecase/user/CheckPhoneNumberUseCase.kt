package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  CheckPhoneNumberUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CheckPhoneNumberUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String): Boolean {
        return userRepository.checkPhoneNumber(phoneNumber)
    }
}
