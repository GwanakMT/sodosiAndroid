package com.sodosi.ui.sodosi

import androidx.lifecycle.viewModelScope
import com.skt.Tmap.TMapData
import com.sodosi.ui.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *  CreateSodosiViewModel.kt
 *
 *  Created by Minji Jeong on 2022/04/20
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CreateMomentViewModel: BaseViewModel() {
    private val tMapData: TMapData by lazy { TMapData() }

    private val _gpsAddress: MutableStateFlow<String> = MutableStateFlow("")
    val gpsAddress: StateFlow<String> = _gpsAddress.asStateFlow()

    fun convertGpsToAddress(longitude: Double, latitude: Double, isLive: Boolean) {
        // TODO: 장소명을 검색할 수 있는 API가 없어서 이 부분 생략될 가능성 있음. 기획 확인 필요
        viewModelScope.launch(Dispatchers.IO) {
            val address = tMapData.reverseGeocoding(latitude, longitude, "A00")?.strFullAddress ?: ""
            _gpsAddress.emit("${if(isLive) "현위치: " else ""}${address.split(",").last()}")
        }
    }
}