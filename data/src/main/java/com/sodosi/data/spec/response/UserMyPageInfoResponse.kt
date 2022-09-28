package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  UserMyPageInfoResponse.kt
 *
 *  Created by Minji Jeong on 2022/09/29
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserMyPageInfoResponse(
    val nickname: String = "",              // 닉네임
    val madeSodosiCount: Int = 0,           // 내가 만든 소도시
    val participateSodosiCount: Int = 0,    // 참여 중인 소도시
    val bookmarkSodosiCount: Int = 0,       // 북마크
)
