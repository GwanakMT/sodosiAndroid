package com.sodosi.domain.repository

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Terms
import com.sodosi.domain.entity.User

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
        agreeInfoMap: Map<String, Boolean>
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

    // 로그인 without password
    suspend fun signInWithoutPassword(
        phoneNumber: String
    ): Result<Boolean>

    // 소도시 생성 여부(has-sodosi)
    suspend fun hasSodosi(): Boolean

    // 마이페이지 정보 조회
    suspend fun getMyPageInfo(): Result<User>

    suspend fun unregisterUser(): Result<Unit>

    suspend fun checkCurrentPassword(password: String): Result<Boolean>
    suspend fun changePhoneNumber(phoneNumber: String): Result<Boolean>
    suspend fun changePassword(password: String): Result<Boolean>
    suspend fun changeNickName(nickName: String): Result<Boolean>
}
