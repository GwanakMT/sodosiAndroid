package com.sodosi.data.repository

import com.sodosi.data.mapper.MomentMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.MomentRequest
import com.sodosi.data.spec.request.ReportRequest
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.entity.Place
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
            contents = contents,
        )

        return try {
            val result = sodosiApi.postMoment(sodosiId, request)
            Result.Success(momentMapper.mapToEntity(result.data))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPlaceListBySodosi(sodosiId: Long): Result<List<Place>> {
        return try {
            val result = sodosiApi.getPlaceListBySodosi(sodosiId)
            Result.Success(result.data.map { momentMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMyMomentList(): Result<List<Moment>> {
        return try {
            val result = sodosiApi.getMyMomentList()
            Result.Success(result.data.map { momentMapper.mapToEntity(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun reportMoment(sodosiId: Long, momentId: Long, reason: String): Result<Unit> {
        return try {
            val request = ReportRequest(moment_id = momentId, reason = reason)
            sodosiApi.repostMoment(sodosiId, request)

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
