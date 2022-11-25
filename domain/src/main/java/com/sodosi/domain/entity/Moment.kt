package com.sodosi.domain.entity

/**
 *  Moment.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Moment(
    val id: Long,
    val contents: String,
    val latitude: Double,
    val longitude: Double,
    val userName: String,
    val jibunAddress: String,
    val roadAddress: String,
    val addressDetail: String,
    val commentCount: String,
    val createdDateTime: String,
    val updatedDateTime: String,
    val momentImagesSet: List<String>,
    val timeInfo: String,
    val sodosiId: Long,
    val sodosiName: String,
)
