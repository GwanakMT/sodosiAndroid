package com.sodosi.domain.entity

/**
 *  Comment.kt
 *
 *  Created by Minji Jeong on 2022/02/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

data class Comment(
    val id: String = "",
    val profileImage: String = "",
    val nickname: String = "",
    val comment: String = "",
    val updateTime: String = "",
    val location: String = "",
    val latitude: Long = 0L,
    val longitude: Long = 0L,
)
