package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  SignInUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val setPhoneNumberUseCase: SetPhoneNumberUseCase,
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String,
    ): Result<Boolean> {
        val result = userRepository.signIn(phoneNumber, password)
        if (result is Result.Success) setPhoneNumberUseCase(phoneNumber)

        return result
    }
}
