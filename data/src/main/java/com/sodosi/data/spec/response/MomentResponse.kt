package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  MomentResponse.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class MomentResponse(
    val id: Long,
    val contents: String,
    val userName: String,
    val latitude: Double,
    val longitude: Double,
    val jibunAddress: String,
    val roadAddress: String,
    val addressDetail: String,
    val commentCount: String,
    val createdDateTime: String,
    val updatedDateTime: String,
    val momentImagesSet: List<String>,
    val timeInfo: String,
)
