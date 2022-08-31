package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  UserSignInRequest.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserSignInRequest(
    val phone: String = "",     // 전화번호
    val password: String = "",  // 비밀번호
)
