package com.sodosi.data.repository

import com.sodosi.data.mapper.MomentMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.MomentRequest
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.repository.MomentRepository
import javax.inject.Inject

/**
 *  MomentRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentRepositoryImpl @Inject constructor(
    private val sodosiApi: SodosiApi,
    private val momentMapper: MomentMapper,
) : MomentRepository {
    override suspend fun postMoment(
        sodosiId: Long,
        latitude: Double,
        longitude: Double,
        roadAddress: String,
        jibunAddress: String,
        addressDetail: String,
        contents: String
    ): Result<Moment> {
        val request = MomentRequest(
            latitude = latitude,
            longitude = longitude,
            roadAddress = roadAddress,
            jibunAddress = jibunAddress,
            addressDetail = addressDetail,
            content = contents,
        )

        return try {
            val result = sodosiApi.postMoment(sodosiId, request)
            Result.Success(momentMapper.mapToEntity(result.data))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
