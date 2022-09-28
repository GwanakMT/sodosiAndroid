package com.sodosi.data.repository

import com.sodosi.data.mapper.UserPrivacyPolicyMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.UserSignInRequest
import com.sodosi.data.spec.request.UserSignUpRequest
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Terms
import com.sodosi.domain.repository.UserRepository
import com.sodosi.domain.usecase.token.SetTokenUseCase
import javax.inject.Inject

/**
 *  UserRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class UserRepositoryImpl @Inject constructor(
    private val sodosiApi: SodosiApi,
    private val setTokenUseCase: SetTokenUseCase,
    private val userPrivacyPolicyMapper: UserPrivacyPolicyMapper,
) : UserRepository {

    override suspend fun signUp(
        phoneNumber: String,
        password: String,
        name: String,
        nickName: String,
        agreeInfoMap: Map<String, Boolean>
    ): Result<Pair<Boolean, String>> {
        val request = UserSignUpRequest(
            phone = phoneNumber,
            password = password,
            name = name,
            nickname = nickName,
            agreeInfoMap = agreeInfoMap
        )

        return try {
            val result = sodosiApi.signUp(request)
            setTokenUseCase(result.data.token)

            if (result.data.token.isEmpty()) {
                Result.Success(Pair(false, result.message))
            } else {
                Result.Success(Pair(true, result.message))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun checkPhoneNumber(phoneNumber: String): Boolean {
        return try {
            val result = sodosiApi.checkPhoneNumber(phoneNumber)
            result.data.code == "NOT_JOINED"
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserPrivacyPolicyContents(): Result<List<Terms>> {
        return try {
            val result = sodosiApi.getUserPrivacyPolicyContents()
            Result.Success(userPrivacyPolicyMapper.mapToEntity(result.data))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun signIn(
        phoneNumber: String,
        password: String,
    ): Result<Boolean> {
        val request = UserSignInRequest(
            phone = phoneNumber,
            password = password
        )

        return try {
            val result = sodosiApi.signIn(request)
            setTokenUseCase(result.data.token)

            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun hasSodosi(): Boolean {
        return try {
            val result = sodosiApi.hasSodosi()
            result.data.hasSodosi
        } catch (e: Exception) {
            true
        }
    }
}