package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  CreateSodosiRequest.kt
 *
 *  Created by Minji Jeong on 2022/09/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class CreateSodosiRequest(
    val name: String,               // 소도시 이름
    val icon: String,               // 아이콘(이모지)
    val viewStatus: Boolean,        // 공개 여부. true: 공개, false: 비공개
)
