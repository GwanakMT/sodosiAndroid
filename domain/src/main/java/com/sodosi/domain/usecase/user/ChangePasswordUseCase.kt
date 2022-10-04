package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  ChangePasswordUseCase.kt
 *
 *  Created by Minji Jeong on 2022/10/04
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(password: String): Result<Boolean> {
        return userRepository.changePassword(password)
    }
}