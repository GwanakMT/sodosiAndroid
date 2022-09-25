package com.sodosi.data.network.api

import com.sodosi.data.spec.request.CreateSodosiRequest
import com.sodosi.data.spec.request.UserSignInRequest
import com.sodosi.data.spec.request.UserSignUpRequest
import com.sodosi.data.spec.response.*
import retrofit2.http.*

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

    // 메인화면에 보여질 소도시 목록
    @GET("/api/v1/sodosis/main")
    suspend fun getMainSodosiList(): BaseResponse<MainSodosiListsResponse>

    // 소도시 전체 목록
    @GET("/api/v1/sodosis")
    suspend fun getAllSodosiList(@Query("sortSodosiCode") sortSodosiCode: String): BaseResponse<List<SodosiResponse>>

    // 내가 만든 소도시 목록
    @GET("/api/v1/users/sodosis")
    suspend fun getCreatedSodosiList(): BaseResponse<List<SodosiResponse>>

    // 북마크한 소도시 목록
    @GET("/api/v1/users/bookmarks")
    suspend fun getMarkedSodosiList(): BaseResponse<List<SodosiResponse>>

    // 관심 소도시 등록
    @POST("/api/v1/sodosis/{id}/bookmark")
    suspend fun markSodosi(@Path("id") id: Long): BaseResponse<SodosiResponse>

    // 관심 소도시 해지
    @DELETE("/api/v1/bookmarks/{id}")
    suspend fun unmarkSodosi(@Path("id") id: Long): BaseResponse<EmptyBody>

    // 소도시 생성한 적 있는지 여부
    @GET("/api/v1/users/has-sodosi")
    suspend fun hasSodosi(): BaseResponse<HasSodosiResponse>
}
