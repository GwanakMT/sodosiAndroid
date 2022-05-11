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
                    photo = listOf("")
                ),
                PlaceModel(
                    id = "",
                    placeName = "GANADARA",
                    userName = "Jay Park",
                    userProfile = "",
                    dateTime = "",
                    comment = "청춘 무한한 속에서 천하를 인간에 피가 따뜻한 청춘의 열락의 운다. 인생에 가는 피고, 생명을 노려버리기...",
                    photo = listOf("", "")
                ),
                PlaceModel(
                    id = "",
                    placeName = "취향로 3가",
                    userName = "메롱",
                    userProfile = "",
                    dateTime = "",
                    comment = "여기 진짜 맛있음 ㅋㅋ",
                    photo = listOf("", "", "")
                ),
            )
        }
    }
}
