package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  MarkSodosiRequest.kt
 *
 *  Created by Minji Jeong on 2022/09/15
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class MarkSodosiRequest(
    val sodosiid: Long,
)
