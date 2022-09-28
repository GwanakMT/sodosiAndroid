package com.sodosi.data.repository

import com.sodosi.data.mapper.SodosiMapper
import com.sodosi.data.network.api.SodosiApi
import com.sodosi.data.spec.request.CreateSodosiRequest
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
            val bannerSodosiList = HashMap<SodosiCategory, List<Sodosi>>()

            val bannerList = result.data.bannerSodosiList
            bannerSodosiList[SodosiCategory.MAIN_BANNER] = bannerList.map { sodosiMapper.mapToEntitiy(it) }

            // 내가 참여중인 소도시
            bannerSodosiList[SodosiCategory.COMMENTED] = result.data.mySodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            }

            // 내 관심 소도시
            bannerSodosiList[SodosiCategory.MARKED] = result.data.interestSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            }

            // 지금 HOT한 소도시
            bannerSodosiList[SodosiCategory.HOT] = result.data.hotSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            }

            // 새롭게 추천하는 소도시
            bannerSodosiList[SodosiCategory.NEW] = result.data.newSodosiList.map {
                sodosiMapper.mapToEntitiy(it)
            }

            Result.Success(Pair(result.data.hasSodosi, bannerSodosiList))
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

    override suspend fun getCreatedSodosiList(): Result<List<Sodosi>> {
        return try {
            val result = sodosiApi.getCreatedSodosiList()
            val sodosiList = result.data.map { sodosiMapper.mapToEntitiy(it) }
            Result.Success(sodosiList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMarkedSodosiList(): Result<List<Sodosi>> {
        return try {
            val result = sodosiApi.getMarkedSodosiList()
            val sodosiList = result.data.map { sodosiMapper.mapToEntitiy(it) }
            Result.Success(sodosiList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun markSodosi(id: Long): Result<Boolean> {
        return try {
            val result = sodosiApi.markSodosi(id)
            Result.Success(result.data.marked)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun unmarkSodosi(id: Long): Result<Boolean> {
        return try {
            val result = sodosiApi.unmarkSodosi(id)
            Result.Success(result.code != 200) // 200이면 mark 해제된 것이므로 false 리턴
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        private val DEFAULT_BANNER_LIST = listOf(
            Sodosi(
                id = 0,
                name = "똥손인 나도\n여기서 찍으면 인생샷!",
                momentCount = 0,
                userCount = 0,
                icon = "camera",
                momentImage = null,
                isMarked = false
            ),
            Sodosi(
                id = 1,
                name = "댕댕이를 위한\n베스트 산책 코스",
                momentCount = 0,
                userCount = 0,
                icon = "dog",
                momentImage = null,
                isMarked = false
            ),
            Sodosi(
                id = 2,
                name = "나만 알고 싶은\n공부하기 좋은 카페",
                momentCount = 0,
                userCount = 0,
                icon = "cafe",
                momentImage = null,
                isMarked = false
            ),
            Sodosi(
                id = 3,
                name = "직접 가보면\n을씨년스러운 장소",
                momentCount = 0,
                userCount = 0,
                icon = "danger",
                momentImage = null,
                isMarked = false
            ),
        )
    }
}
