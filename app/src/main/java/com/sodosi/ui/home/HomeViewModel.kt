package com.sodosi.ui.home

import com.sodosi.ui.common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *  HomeViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class HomeViewModel : BaseViewModel() {
    private val _mapPreviewList = MutableStateFlow<List<MapPreview>>(listOf())
    val mapPreviewList: StateFlow<List<MapPreview>> = _mapPreviewList

    fun getMapPreviewList() {
        _mapPreviewList.value = listOf(
            MapPreview("우리 동네 힙한 장소", ""),
            MapPreview("아무렇게나 찍어도 인생샷", ""),
            MapPreview("을씨년스러운 장소들", ""),
            MapPreview("지금 우리 동네는 (자유게시판)", ""),
            MapPreview("흑역사 양성소(명예의 전당)", ""),
        )
    }
}
