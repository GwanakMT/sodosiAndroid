package com.sodosi.domain.repository

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.entity.SodosiCategory

/**
 *  SodosiRepository.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface SodosiRepository {
    suspend fun createSodosi(name: String, icon: String, viewState: Boolean): Result<Long>

    suspend fun getMainSodosiList(): Result<Pair<Boolean, Map<SodosiCategory, List<Sodosi>>>>
    suspend fun getAllSodosiList(sortBy: String): Result<List<Sodosi>>

    suspend fun markSodosi(id: Long): Result<Boolean>
    suspend fun unmarkSodosi(id: Long): Result<Boolean>
}