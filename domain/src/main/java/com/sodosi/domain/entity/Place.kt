package com.sodosi.domain.entity

/**
 *  Place.kt
 *
 *  Created by Minji Jeong on 2022/11/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class Place(
    val addressDetail: String,
    val momentsList: List<Moment>,
)
