package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  SignUpUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        password: String,
        name: String,
        nickName: String,
        agreeInfoMap: Map<String, String>,
    ): Boolean {
        return try {
            val result = userRepository.signUp(
                phoneNumber,
                password,
                name,
                nickName,
                agreeInfoMap
            )

            when(result) {
                is Result.Success -> true
                is Result.Error -> false
            }
        } catch (e: Exception) {
            false
        }
    }
}
