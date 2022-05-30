package com.sodosi.ui.sodosi.model

/**
 *  PlaceModel.kt
 *
 *  Created by Minji Jeong on 2022/05/12
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class PlaceModel(
    val id: String,
    val placeName: String,
    val placeAddress: String,
    val userName: String,
    val userProfile: String,
    val dateTime: String,
    val comment: String,
    val photo: List<String>,
    val longitude: Double,
    val latitude: Double,
)