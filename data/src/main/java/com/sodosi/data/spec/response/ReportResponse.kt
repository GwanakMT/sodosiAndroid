package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  ReportResponse.kt
 *
 *  Created by Minji Jeong on 2022/11/26
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class ReportResponse(
    val isSuccess: Boolean,
)