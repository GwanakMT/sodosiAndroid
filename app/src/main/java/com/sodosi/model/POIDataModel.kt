package com.sodosi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  POIDataModel.kt
 *
 *  Created by Minji Jeong on 2022/09/10
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@Parcelize
data class POIDataModel(
    val placeName: String,  // 장소명
    val address: String,    // 도로명 주소
    val latitude: String,   // 위도
    val longitude: String,  // 경도
): Parcelable
