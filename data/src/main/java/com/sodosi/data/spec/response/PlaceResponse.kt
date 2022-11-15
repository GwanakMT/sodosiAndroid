package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  PlaceResponse.kt
 *
 *  Created by Minji Jeong on 2022/11/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class PlaceResponse(
    val addressDetail: String,
    val momentsList: List<MomentResponse>,
)