package com.sodosi.domain.usecase.user

import com.sodosi.domain.usecase.token.SetTokenUseCase
import javax.inject.Inject

/**
 *  LogoutUseCase.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class LogoutUseCase @Inject constructor(
    private val setTokenUseCase: SetTokenUseCase,
    private val setPhoneNumberUseCase: SetPhoneNumberUseCase,
) {
    suspend operator fun invoke() {
        setTokenUseCase("")
        setPhoneNumberUseCase("")
    }
}
