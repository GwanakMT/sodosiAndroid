package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  SignInWithoutPasswordUseCase.kt
 *
 *  Created by Minji Jeong on 2022/10/29
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SignInWithoutPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val setPhoneNumberUseCase: SetPhoneNumberUseCase,
) {
    suspend operator fun invoke(
        phoneNumber: String
    ): Result<Boolean> {
        val result = userRepository.signInWithoutPassword(phoneNumber)
        if (result is Result.Success) setPhoneNumberUseCase(phoneNumber)

        return result
    }
}
