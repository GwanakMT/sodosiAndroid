package com.sodosi.data.mapper

import com.sodosi.data.spec.response.SodosiResponse
import com.sodosi.domain.entity.Sodosi
import javax.inject.Inject

/**
 *  SodosiMapper.kt
 *
 *  Created by Minji Jeong on 2022/09/15
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiMapper @Inject constructor() {
    fun mapToEntitiy(spec: SodosiResponse): Sodosi {
        return Sodosi(
            id = spec.id,
            name = spec.name,
            momentCount = spec.momentCount,
            userCount = spec.userCount,
            icon = spec.icon,
            momentImage = spec.momentImage,
            isMarked = spec.marked,
            isMine = spec.isMine,
        )
    }
}