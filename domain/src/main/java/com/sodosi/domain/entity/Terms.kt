package com.sodosi.domain.entity

/**
 *  Terms.kt
 *
 *  Created by Minji Jeong on 2022/03/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Terms(
    val id: Long,
    val title: String,
    val content: String,
    val essential: Boolean,
    var isAllow: Boolean = false,
)
