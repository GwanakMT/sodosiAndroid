package com.sodosi.domain.repository

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Sodosi

/**
 *  SodosiRepository.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface SodosiRepository {
    suspend fun createSodosi(name: String, icon: String, viewState: Boolean): Result<Long>

    suspend fun getMainSodosiList(): List<Sodosi>
    suspend fun getCommentedSodosiList(): List<Sodosi>
    suspend fun getBookmarkSodosiList(): List<Sodosi>
    suspend fun getHotSodosiList(): List<Sodosi>
    suspend fun getNewSodosiList(): List<Sodosi>
    suspend fun getAllSodosiList(sortBy: String): Result<List<Sodosi>>
}