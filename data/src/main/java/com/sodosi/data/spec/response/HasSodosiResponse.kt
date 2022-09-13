package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  HasSodosiResponse.kt
 *
 *  Created by Minji Jeong on 2022/09/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class HasSodosiResponse(
    val hasSodosi: Boolean = true
)
