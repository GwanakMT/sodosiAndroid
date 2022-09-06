package com.sodosi.ui.mypage

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.usecase.user.GetLastVisitedTimeUseCase
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  MypageViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val getLastVisitedTimeUseCase: GetLastVisitedTimeUseCase,
) : BaseViewModel() {

    private val _userBaseProfile = MutableSharedFlow<Triple<String, String, String>>() // Name, Profile Image, Last Visited Time
    val userBaseProfile: SharedFlow<Triple<String, String, String>> = _userBaseProfile.asSharedFlow()

    init {
        getUserBaseProfile()
    }

    private fun getUserBaseProfile() {
        viewModelScope.launch {
            val nickname = "중구구립도서관"
            val profileImage = ""
            val lastVisitedTime = getLastVisitedTimeUseCase(System.currentTimeMillis())

            _userBaseProfile.emit(Triple(nickname, profileImage, lastVisitedTime))
        }
    }
}
