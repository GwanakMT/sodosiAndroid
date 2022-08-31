package com.sodosi.data.spec.response

import kotlinx.serialization.Serializable

/**
 *  UserPrivacyPolicyContents.kt
 *
 *  Created by Minji Jeong on 2022/08/23
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Serializable
data class UserPrivacyPolicyContentsResponse(
    val results: List<PrivacyPolicy>
) {
    @Serializable
    data class PrivacyPolicy(
        val id: Long = 0,                   // 약관 id
        val kind: String = "",              // 약관 종류
        val contents: String = "",          // 약관 내용
        val essential: Boolean = false,     // 필수 약관 여부
        val version: Int = 0,               // 약관버전
    )
}