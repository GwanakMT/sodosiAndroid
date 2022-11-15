package com.sodosi.model

import com.sodosi.ui.sodosi.model.MomentModel

/**
 *  PlaceModel.kt
 *
 *  Created by Minji Jeong on 2022/11/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

data class PlaceModel(
    val addressDetail: String,
    val momentList: List<MomentModel>,
)
