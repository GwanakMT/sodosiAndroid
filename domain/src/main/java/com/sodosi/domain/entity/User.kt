package com.sodosi.domain.entity

/**
 *  User.kt
 *
 *  Created by Minji Jeong on 2022/09/29
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class User(
    val nickName: String,
    val madeSodosiCount: Int,
    val participateSodosiCount: Int,
    val bookmarkSodosiCount: Int,
)
