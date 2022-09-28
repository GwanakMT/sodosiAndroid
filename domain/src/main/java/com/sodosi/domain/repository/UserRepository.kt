package com.sodosi.domain.repository

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Terms

/**
 *  UserRepository.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface UserRepository {
    // 회원가입
    suspend fun signUp(
        phoneNumber: String,
        password: String,
        name: String,
        nickName: String,
        agreeInfoMap: Map<String, String>
    ): Result<Pair<Boolean, String>>

    // 전화번호 중복 확인
    suspend fun checkPhoneNumber(phoneNumber: String): Boolean

    // 사용자 이용 약관 정보
    suspend fun getUserPrivacyPolicyContents(): Result<List<Terms>>

    // 로그인
    suspend fun signIn(
        phoneNumber: String,
        password: String,
    ): Result<Boolean>

    // 소도시 생성 여부(has-sodosi)
    suspend fun hasSodosi(): Boolean
}
