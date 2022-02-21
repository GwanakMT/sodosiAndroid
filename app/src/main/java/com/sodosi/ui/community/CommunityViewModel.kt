package com.sodosi.ui.community

import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.domain.entity.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *  CommunityViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CommunityViewModel : BaseViewModel() {
    private val _nowComment = MutableStateFlow<List<Comment>>(listOf())
    val nowComment: StateFlow<List<Comment>> = _nowComment

    fun getCommunityNowComment() {
        _nowComment.value = listOf(
            Comment(nickname = "중구도서관", comment = "여기 맘스터치 허물고 공사중이네.\n뭐가 들어올까요?", location = "남산타운 37동"),
            Comment(nickname = "김치볶음밥", comment = "야경 이쁜 곳. 밤산책 강추!", location = "남산타운 37동"),
            Comment(nickname = "숭린이", comment = "바나나 케이크 맛집입니다.", location = "남산타운 37동"),
            Comment(nickname = "구교환", comment = "메기 촬영지", location = "남산타운 37동"),
            Comment(nickname = "이옥섭의 강아지 겨울짱", comment = "여기 강아지 산책하기 너무 좋아요!", location = "남산타운 37동"),
        )
    }
}
