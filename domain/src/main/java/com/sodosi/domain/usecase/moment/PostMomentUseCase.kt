package com.sodosi.domain.usecase.moment

import com.sodosi.domain.Result
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.repository.MomentRepository
import javax.inject.Inject

/**
 *  PostMomentUseCase.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PostMomentUseCase @Inject constructor(private val momentRepository: MomentRepository) {
    suspend operator fun invoke(
        sodosiId: Long,
        latitude: Double,
        longitude: Double,
        roadAddress: String,
        jibunAddress: String,
        addressDetail: String,
        contents: String,
        imageList: List<String>,
    ): Result<Moment> {
        return momentRepository.postMoment(
            sodosiId = sodosiId,
            latitude = latitude,
            longitude = longitude,
            roadAddress = roadAddress,
            jibunAddress = jibunAddress,
            addressDetail = addressDetail,
            contents = contents,
            imageList = imageList,
        )
    }
}