package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  BaseResponse.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class BaseResponse<T>(
    val code: Int = 0,
    val message: String = "",
    val data: T
)
