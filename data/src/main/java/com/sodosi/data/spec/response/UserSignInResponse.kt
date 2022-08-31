package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  UserSignInResponse.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserSignInResponse(
    val token: String = "",         // 로그인 토큰
)
