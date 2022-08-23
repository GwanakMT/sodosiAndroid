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
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String,
    ): Result<Boolean> {
        return userRepository.signIn(phoneNumber, password)
    }
}
