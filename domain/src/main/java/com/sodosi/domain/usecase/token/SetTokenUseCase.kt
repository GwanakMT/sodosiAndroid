package com.sodosi.domain.usecase.token

import com.sodosi.domain.repository.TokenRepository
import javax.inject.Inject

/**
 *  SetTokenUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SetTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
){
    operator fun invoke(accessToken: String) {
        tokenRepository.setToken(accessToken)
    }
}
