package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  ChangePasswordRequest.kt
 *
 *  Created by Minji Jeong on 2022/10/04
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class ChangePasswordRequest(
    val password: String,
)
