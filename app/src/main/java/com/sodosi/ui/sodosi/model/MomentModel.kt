package com.sodosi.ui.sodosi.model

/**
 *  MomentModel.kt
 *
 *  Created by Minji Jeong on 2022/05/12
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class MomentModel(
    val id: Long,
    val contents: String,
    val latitude: Double,
    val longitude: Double,
    val userName: String,
    val jibunAddress: String,
    val roadAddress: String,
    val addressDetail: String,
    val photo: List<String>,
    val timeInfo: String,
)