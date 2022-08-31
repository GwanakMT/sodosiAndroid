package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  UsersCheckPhoneNumberResponse.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UsersCheckPhoneNumberResponse(
    val valid: Boolean = false,     // 전화번호 중복 여부
    val code: String = "",          // 가입 여부
)
