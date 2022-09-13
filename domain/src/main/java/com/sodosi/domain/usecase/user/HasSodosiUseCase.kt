package com.sodosi.domain.usecase.user

import com.sodosi.domain.repository.UserRepository
import javax.inject.Inject

/**
 *  HasSodosiUseCase.kt
 *
 *  Created by Minji Jeong on 2022/09/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class HasSodosiUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(): Boolean {
        return userRepository.hasSodosi()
    }
}
