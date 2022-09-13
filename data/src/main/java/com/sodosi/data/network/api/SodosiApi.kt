package com.sodosi.data.network.api

import com.sodosi.data.spec.request.CreateSodosiRequest
import com.sodosi.data.spec.request.UserSignInRequest
import com.sodosi.data.spec.request.UserSignUpRequest
import com.sodosi.data.spec.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  SodosiApi.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface SodosiApi {

    // 전화번호 중복 확인
    @GET("/api/v1/users/check/phone-number")
    suspend fun checkPhoneNumber(@Query("phoneNumber") phoneNumber: String): BaseResponse<UsersCheckPhoneNumberResponse>

    // 사용자 이용 약관 정보
    @GET("/api/v1/users/user-privacy-policy-contents")
    suspend fun getUserPrivacyPolicyContents(): BaseResponse<UserPrivacyPolicyContentsResponse >

    // 회원가입
    @POST("/api/v1/users")
    suspend fun signUp(@Body signUpRequest: UserSignUpRequest): BaseResponse<UserSignUpResponse>

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun signIn(@Body signInRequest: UserSignInRequest): BaseResponse<UserSignInResponse>

    // 소도시 생성
    @POST("/api/v1/sodosis")
    suspend fun createSodosi(@Body createSodosiRequest: CreateSodosiRequest): BaseResponse<CreateSodosiResponse>

    // 소도시 생성한 적 있는지 여부
    @GET("/api/v1/users/has-sodosi")
    suspend fun hasSodosi(): BaseResponse<HasSodosiResponse>
}
