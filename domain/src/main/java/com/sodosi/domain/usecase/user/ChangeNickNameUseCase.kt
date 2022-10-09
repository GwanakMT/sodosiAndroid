package com.sodosi.domain.usecase.user

import com.sodosi.domain.Result
import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  ChangeNickNameUseCase.kt
 *
 *  Created by Minji Jeong on 2022/10/09
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class ChangeNickNameUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(nickName: String): Result<Boolean> {
        return userRepository.changeNickName(nickName)
    }
}
