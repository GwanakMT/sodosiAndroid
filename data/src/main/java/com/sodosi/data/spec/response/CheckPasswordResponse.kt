package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  CheckPasswordResponse.kt
 *
 *  Created by Minji Jeong on 2022/10/04
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class CheckPasswordResponse(
    val message: String = "",       // 메세지
    val valid: Boolean = false,     // 일치 여부
)
