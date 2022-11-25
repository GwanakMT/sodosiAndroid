package com.sodosi.data.network.api

import com.sodosi.data.spec.request.*
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
    @GET("/api/v1/user-privacy-policy-contents")
    suspend fun getUserPrivacyPolicyContents(): BaseResponse<List<UserPrivacyPolicyContentsResponse>>

    // 회원가입
    @POST("/api/v1/users")
    suspend fun signUp(@Body signUpRequest: UserSignUpRequest): BaseResponse<UserSignUpResponse>

    // 로그인
    @POST("/api/v1/users/login")
    suspend fun signIn(@Body signInRequest: UserSignInRequest): BaseResponse<UserSignInResponse>

    // 로그인 without password
    @POST("/api/v1/users/login/phone")
    suspend fun signInWithoutPassword(@Body signInRequest: UserSignInRequest): BaseResponse<UserSignInResponse>

    // 회원 탈퇴
    @POST("/api/v1/users/unregister")
    suspend fun unregister(): BaseResponseWithNull<Unit>

    // 소도시 생성
    @POST("/api/v1/sodosis")
    suspend fun createSodosi(@Body createSodosiRequest: CreateSodosiRequest): BaseResponse<CreateSodosiResponse>

    // 소도시 수정
    @PATCH("/api/v1/sodosis/{id}")
    suspend fun patchSodosi(@Path("id") id: Long, @Body createSodosiRequest: CreateSodosiRequest): BaseResponse<CreateSodosiResponse>

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

    // 내가 참여한 소도시
    @GET("/api/v1/sodosis/PARTICIPATE/type")
    suspend fun getCommentedSodosiList(): BaseResponse<List<SodosiResponse>>

    // 관심 소도시 등록
    @POST("/api/v1/sodosis/{id}/bookmark")
    suspend fun markSodosi(@Path("id") id: Long): BaseResponse<SodosiResponse>

    // 관심 소도시 해지
    @DELETE("/api/v1/sodosis/{id}/bookmark")
    suspend fun unmarkSodosi(@Path("id") id: Long): BaseResponse<EmptyBody?>

    // 소도시 생성한 적 있는지 여부
    @GET("/api/v1/users/has-sodosi")
    suspend fun hasSodosi(): BaseResponse<HasSodosiResponse>

    // 마이페이지 정보 조회
    @GET("/api/v1/users")
    suspend fun getMyPageInfo(): BaseResponse<UserMyPageInfoResponse>

    // 비밀번호 변경하기 전 일치여부 확인
    @GET("/api/v1/users/check/password")
    suspend fun checkCurrentPassword(@Query("password") password: String): BaseResponse<CheckPasswordResponse>

    // 비밀번호 변경
    @PUT("/api/v1/users/password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): BaseResponse<UserSignInResponse>

    // 닉네임 변경
    @PUT("/api/v1/users/nickname")
    suspend fun changeNickName(@Query("nickname") nickName: String): BaseResponse<UserSignInResponse>

    // 순간 POST
    @POST("/api/v1/sodosis/{id}/moments")
    suspend fun postMoment(@Path("id") id: Long, @Body momentRequest: MomentRequest): BaseResponse<MomentResponse>

    // 순간 GET (장소기준)
    @GET("/api/v1/sodosis/{sodosiId}/moments")
    suspend fun getPlaceListBySodosi(@Path("sodosiId") id: Long): BaseResponse<List<PlaceResponse>>

    // 최근 내가 남긴 순간
    @GET("/api/v1/users/moments")
    suspend fun getMyMomentList(): BaseResponse<List<MomentResponse>>

    // 순간 신고
    @POST("/api/v1/moments/{sodosiId}}/report")
    suspend fun repostMoment(@Path("sodosiId") id: Long, report: ReportRequest): BaseResponse<ReportResponse>
}
