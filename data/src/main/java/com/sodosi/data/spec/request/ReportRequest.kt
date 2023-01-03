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
    val sodosi_id: Long,
    val moment_id: Long? = null, // 소도시 신고는 moment_id 없어도 됨
    val reason: String,
)