package com.sodosi.data.mapper

import com.sodosi.data.spec.response.MomentResponse
import com.sodosi.data.spec.response.PlaceResponse
import com.sodosi.domain.entity.Moment
import com.sodosi.domain.entity.Place
import javax.inject.Inject

/**
 *  MomentMapper.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentMapper @Inject constructor() {
    fun mapToEntity(spec: MomentResponse): Moment {
        return Moment(
            id = spec.id,
            contents = spec.contents,
            latitude = spec.latitude,
            longitude = spec.longitude,
            userName = spec.userName,
            jibunAddress = spec.jibunAddress,
            roadAddress = spec.roadAddress,
            addressDetail = spec.addressDetail,
            commentCount = spec.commentCount,
            createdDateTime = spec.createdDateTime,
            updatedDateTime = spec.updatedDateTime,
            momentImagesSet = spec.momentImagesSet.map { it.images },
            timeInfo = spec.timeInfo
        )
    }

    fun mapToEntity(spec: PlaceResponse): Place {
        return Place(
            addressDetail = spec.addressDetail,
            momentsList = spec.momentsList.map { mapToEntity(it) }
        )
    }
}