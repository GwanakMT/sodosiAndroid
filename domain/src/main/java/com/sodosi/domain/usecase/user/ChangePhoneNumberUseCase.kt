package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  ChangePhoneNumberUseCase.kt
 *
 *  Created by Minji Jeong on 2022/12/21
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ChangePhoneNumberUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(phoneNumber: String): Result<Boolean> {
        return userRepository.changePhoneNumber(phoneNumber)
    }
}
