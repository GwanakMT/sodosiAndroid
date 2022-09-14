package com.sodosi.model.mapper

import com.sodosi.domain.entity.Sodosi
import com.sodosi.model.SodosiModel
import javax.inject.Inject

/**
 *  SodosiMapper.kt
 *
 *  Created by Minji Jeong on 2022/09/15
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiMapper @Inject constructor() {
    fun mapToModel(entity: Sodosi): SodosiModel {
        return SodosiModel(
            id = entity.id,
            name = entity.name,
            momentCount = entity.momentCount,
            userCount = entity.userCount,
            icon = entity.icon,
            momentImage = entity.momentImage,
            marked = entity.marked
        )
    }
}
