package com.sodosi.ui.mypage

import androidx.lifecycle.viewModelScope
import com.sodosi.model.mapper.SodosiMapper
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.sodosi.GetMarkedSodosiListUseCase
import com.sodosi.domain.usecase.user.GetLastVisitedTimeUseCase
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getMarkedSodosiListUseCase: GetMarkedSodosiListUseCase,
    private val sodosiMapper: SodosiMapper,
) : BaseViewModel() {

    private val _userBaseProfile = MutableSharedFlow<Triple<String, String, String>>() // Name, Profile Image, Last Visited Time
    val userBaseProfile: SharedFlow<Triple<String, String, String>> = _userBaseProfile.asSharedFlow()

    private val _sodosiList = MutableEventFlow<Result<List<SodosiModel>>>()
    val sodosiList: EventFlow<Result<List<SodosiModel>>> = _sodosiList.asEventFlow()

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

    fun getMarkedSodosiList() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getMarkedSodosiListUseCase()) {
                is Result.Success -> {
                    val list = result.data.map { sodosiMapper.mapToModel(it) }
                    _sodosiList.emit(Result.Success(list))
                }

                is Result.Error -> {
                    _sodosiList.emit(Result.Error(result.exception))
                }
            }
        }
    }
}
