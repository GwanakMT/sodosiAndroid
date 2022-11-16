package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  ImageResponse.kt
 *
 *  Created by Minji Jeong on 2022/11/17
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class ImageResponse(
    val id: Long,
    val images: String,
)
