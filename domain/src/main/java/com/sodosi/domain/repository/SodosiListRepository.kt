package com.sodosi.domain.repository

import com.sodosi.domain.entity.Sodosi

/**
 *  SodosiListRepository.kt
 *
 *  Created by Minji Jeong on 2022/03/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface SodosiListRepository {
    suspend fun getMainSodosiList(): List<Sodosi>
    suspend fun getHotSodosiList(): List<Sodosi>
    suspend fun getNewSodosiList(): List<Sodosi>
}