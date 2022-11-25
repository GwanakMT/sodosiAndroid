package com.sodosi.domain.repository

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.entity.Place

/**
 *  MomentRepository.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

interface MomentRepository {
    suspend fun postMoment(
        sodosiId: Long,
        latitude: Double,
        longitude: Double,
        roadAddress: String,
        jibunAddress: String,
        addressDetail: String,
        contents: String
    ): Result<Moment>

    suspend fun getPlaceListBySodosi(
        sodosiId: Long,
    ): Result<List<Place>>

    suspend fun getMyMomentList(): Result<List<Moment>>
}
