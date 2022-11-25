package com.sodosi.model.mapper

import com.sodosi.domain.entity.Moment
import com.sodosi.domain.entity.Place
import com.sodosi.model.PlaceModel
import com.sodosi.ui.sodosi.model.MomentModel
import javax.inject.Inject

/**
 *  PlaceMapper.kt
 *
 *  Created by Minji Jeong on 2022/11/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PlaceMapper @Inject constructor() {
    fun mapToModel(entity: Place): PlaceModel {
        return PlaceModel(
            addressDetail = entity.addressDetail,
            momentList = entity.momentsList.map { mapToModel(it) }
        )
    }

    fun mapToModel(entity: Moment): MomentModel {
        return MomentModel(
            id = entity.id,
            contents = entity.contents,
            latitude = entity.latitude,
            longitude = entity.longitude,
            userName = entity.userName,
            jibunAddress = entity.jibunAddress,
            roadAddress = entity.roadAddress,
            addressDetail = entity.addressDetail,
            photo = entity.momentImagesSet,
            timeInfo = entity.timeInfo,
            sodosiId = entity.sodosiId,
            sodosiName = entity.sodosiName,
        )
    }
}