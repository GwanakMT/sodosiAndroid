package com.sodosi.data.repository

import com.sodosi.data.mapper.SodosiMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.CreateSodosiRequest
import com.sodosi.data.spec.request.MarkSodosiRequest
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.entity.SodosiCategory
import com.sodosi.domain.repository.SodosiRepository
import javax.inject.Inject

/**
 *  SodosiRepositoryImpl.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiRepositoryImpl @Inject constructor(
    private val sodosiApi: SodosiApi,
    private val sodosiMapper: SodosiMapper,
) : SodosiRepository {
    override suspend fun createSodosi(
        name: String,
        icon: String,
        viewState: Boolean
    ): Result<Long> {
        val request = CreateSodosiRequest(
            name = name,
            icon = icon,
            viewStatus = viewState
        )

        return try {
            val result = sodosiApi.createSodosi(request)

            Result.Success(result.data.id)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMainSodosiList(): Result<Pair<Boolean, Map<SodosiCategory, List<Sodosi>>>> {
        return try {
            val result = sodosiApi.getMainSodosiList()
            val mainSodosiList = HashMap<SodosiCategory, List<Sodosi>>()

            mainSodosiList.put(SodosiCategory.MAIN_BANNER, emptyList())

            // 내가 참여중인 소도시
            mainSodosiList.put(SodosiCategory.COMMENTED, result.data.mySodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            })

            // 내 관심 소도시
            mainSodosiList.put(SodosiCategory.MARKED, result.data.interestSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            })

            // 지금 HOT한 소도시
            mainSodosiList.put(SodosiCategory.HOT, result.data.hotSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            })

            // 새롭게 추천하는 소도시
            mainSodosiList.put(SodosiCategory.NEW, result.data.newSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            })

            Result.Success(Pair(result.data.hasSodosi, mainSodosiList))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAllSodosiList(sortBy: String): Result<List<Sodosi>> {
        return try {
            val result = sodosiApi.getAllSodosiList(sortBy)
            val allSodosiList = result.data.map {
                sodosiMapper.mapToEntitiy(it)
            }

            Result.Success(allSodosiList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun markSodosi(id: Long): Result<Boolean> {
        val request = MarkSodosiRequest(sodosiid = id)

        return try {
            val result = sodosiApi.markSodosi(request)
            Result.Success(result.data.marked)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun unmarkSodosi(id: Long): Result<Boolean> {
        return try {
            val result = sodosiApi.unmarkSodosi(id)
            Result.Success(result.data.marked)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
