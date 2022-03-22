package com.sodosi.ui.home

import com.sodosi.domain.entity.Sodosi
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
    private val _mapPreviewList = MutableStateFlow<List<Sodosi>>(listOf())
    val mapPreviewList: StateFlow<List<Sodosi>> = _mapPreviewList

    fun getMapPreviewList() {
        _mapPreviewList.value = listOf(
            Sodosi(
                id = 0,
                name = "우리 동네 힙한 장소",
                userId = 0,
                userName = "시장 관악산악회",
                momentCount = "",
                status = "",
                icon = "",
                dateTime = ""
            ),
            Sodosi(
                id = 1,
                name = "똥손인 나도\n여기서 찍으면 인생샷!",
                userId = 0,
                userName = "사진박사척박사",
                momentCount = "",
                status = "",
                icon = "",
                dateTime = ""
            ),
            Sodosi(
                id = 2,
                name = "을씨년스러운 장소들",
                userId = 0,
                userName = "중구시립도서관",
                momentCount = "",
                status = "",
                icon = "",
                dateTime = ""
            ),
            Sodosi(
                id = 3,
                name = "지금 우리 동네는 (자유게시판)",
                userId = 0,
                userName = "이펙티브 소도시",
                momentCount = "",
                status = "",
                icon = "",
                dateTime = ""
            ),
            Sodosi(
                id = 4,
                name = "흑역사 양성소(명예의 전당)",
                userId = 0,
                userName = "헬로뉴월드",
                momentCount = "",
                status = "",
                icon = "",
                dateTime = ""
            )
        )
    }
}
