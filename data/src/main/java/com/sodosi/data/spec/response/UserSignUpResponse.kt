package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  UserSignUpResponse.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserSignUpResponse(
    val token: String = "",         // 로그인 토큰
    val nickname: String = "",      // 유저 이름(닉네임)
)
