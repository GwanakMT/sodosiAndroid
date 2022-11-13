package com.sodosi.data.spec.request

import kotlinx.serialization.Serializable

/**
 *  MomentRequest.kt
 *
 *  Created by Minji Jeong on 2022/10/31
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class MomentRequest(
    val latitude: Double,           // 위도
    val longitude: Double,          // 경도
    val roadAddress: String,        // 도로명 주소
    val jibunAddress: String,       // 지번 주소
    val addressDetail: String,      // 주소 디테일
    val contents: String,            // 글
)