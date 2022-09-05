package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  CreateSodosiResponse.kt
 *
 *  Created by Minji Jeong on 2022/09/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class CreateSodosiResponse(
    val id: Long,           // 소도시 ID
    val name: String,       // 소도시 이름
    val icon: String,       // 아이콘(이모지)
)
