package com.sodosi.ui.sodosi

import androidx.lifecycle.viewModelScope
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.sodosi.model.PlaceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 *  SodosiViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/16
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiViewModel: BaseViewModel() {
    private val _placeList: MutableStateFlow<List<PlaceModel>> = MutableStateFlow(listOf())
    val placeList: StateFlow<List<PlaceModel>> = _placeList

    fun getPlaceList() {
        viewModelScope.launch {
            _placeList.value = listOf(
                PlaceModel(
                    id = "",
                    placeName = "어메이징브루잉컴퍼니",
                    userName = "중구도서관",
                    userProfile = "",
                    dateTime = "",
                    comment = "청춘 무한한 속에서 천하를 인간에 피가 따뜻한 청춘의 열락의 운다. 인생에 가는 피고, 생명을 노려버리기...",
                    photo = listOf(imageUrl)
                ),
                PlaceModel(
                    id = "",
                    placeName = "GANADARA",
                    userName = "Jay Park",
                    userProfile = "",
                    dateTime = "",
                    comment = "청춘 무한한 속에서 천하를 인간에 피가 따뜻한 청춘의 열락의 운다. 인생에 가는 피고, 생명을 노려버리기...",
                    photo = listOf(imageUrl, imageUrl)
                ),
                PlaceModel(
                    id = "",
                    placeName = "취향로 3가",
                    userName = "메롱",
                    userProfile = "",
                    dateTime = "",
                    comment = "여기 진짜 맛있음 ㅋㅋ",
                    photo = listOf(imageUrl, imageUrl, imageUrl, imageUrl)
                ),
            )
        }
    }

    companion object {
        private const val imageUrl = "data:image/jpeg;base64,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"
    }
}
