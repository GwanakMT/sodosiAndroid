package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.entity.User
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  GetUserInfoUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/29
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): Result<User> {
        return userRepository.getMyPageInfo()
    }
}
