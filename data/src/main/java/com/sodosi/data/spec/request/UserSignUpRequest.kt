package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  UserSignUpRequest.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserSignUpRequest(
    val phone: String = "",                                 // 전화번호(아이디)
    val password: String = "",                              // 비밀번호
    val name: String = "",                                  // 유저 이름(본명)
    val nickname: String = "",                              // 유저 이름(닉네임)
    val agreeInfoMap: Map<String, Boolean> = emptyMap(),      // 동의한 약관
)
