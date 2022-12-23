package com.sodosi.ui.post

import androidx.lifecycle.viewModelScope
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.sodosi.model.POIDataModel
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 *  SearchPlaceViewModel.kt
 *
 *  Created by Minji Jeong on 2022/06/04
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SearchPlaceViewModel : BaseViewModel() {
    private val tMapData: TMapData by lazy { TMapData() }

    private val _poiList: MutableStateFlow<List<POIDataModel>> = MutableStateFlow(listOf())
    val poiList: StateFlow<List<POIDataModel>> = _poiList.asStateFlow()

    fun search(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (text.isEmpty()) _poiList.emit(emptyList())
                val titlePoi = tMapData.findAllPOI(text, 10)
                if (titlePoi.isNotEmpty()) {
                    val latitude = titlePoi[0].noorLat.toDouble()
                    val longitude = titlePoi[0].noorLon.toDouble()
                    val tMapPoint = TMapPoint(latitude, longitude)
                    val data = tMapData.findAroundNamePOI(tMapPoint, "")

                    _poiList.value = data.map {
                        val roadAddress = getRoadAddressName(it.noorLat, it.noorLon)
                        val jibunAddress = getJibunAddressName(it.noorLat, it.noorLon)
                        POIDataModel(
                            placeName = it.name ?: "",
                            roadAddress = roadAddress.ifEmpty { "${it.upperAddrName} ${it.roadName}" },
                            jibunAddress = jibunAddress.ifEmpty { "${it.upperAddrName} ${it.poiAddress}" },
                            latitude = it.noorLat ?: "",
                            longitude = it.noorLon ?: "",
                        )
                    }
                }
            } catch (e: Exception) {
                LogUtil.e(e.message.toString())
            }
        }
    }

    private fun getRoadAddressName(lat: String, lon: String): String {
        return try {
            val latitude = lat.toDouble()
            val longitude = lon.toDouble()

            val reverseGeo = tMapData.reverseGeocoding(latitude, longitude, "A10")

            reverseGeo?.strFullAddress?.split(",")?.last() ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    private fun getJibunAddressName(lat: String, lon: String): String {
        return try {
            val latitude = lat.toDouble()
            val longitude = lon.toDouble()

            val reverseGeo = tMapData.reverseGeocoding(latitude, longitude, "A00")

            reverseGeo?.strFullAddress?.split(",")?.last() ?: ""
        } catch (e: Exception) {
            ""
        }
    }
}
