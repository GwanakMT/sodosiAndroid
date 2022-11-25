package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  ReportRequest.kt
 *
 *  Created by Minji Jeong on 2022/11/26
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class ReportRequest(
    val moment_id: Long,
    val reason: String,
)