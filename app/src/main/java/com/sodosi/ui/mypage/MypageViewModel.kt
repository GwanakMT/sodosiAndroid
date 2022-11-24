package com.sodosi.ui.mypage

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.entity.User
import com.sodosi.domain.usecase.sodosi.GetCreatedSodosiListUseCase
import com.sodosi.domain.usecase.sodosi.GetMarkedSodosiListUseCase
import com.sodosi.domain.usecase.user.ChangeNickNameUseCase
import com.sodosi.domain.usecase.sodosi.GetCommentedSodosiListUseCase
import com.sodosi.domain.usecase.sodosi.PatchMarkSodosiUseCase
import com.sodosi.domain.usecase.user.GetLastVisitedTimeUseCase
import com.sodosi.domain.usecase.user.GetUserInfoUseCase
import com.sodosi.model.SodosiModel
import com.sodosi.model.mapper.SodosiMapper
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import com.sodosi.util.LogUtil
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
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getLastVisitedTimeUseCase: GetLastVisitedTimeUseCase,
    private val getCreatedSodosiListUseCase: GetCreatedSodosiListUseCase,
    private val getCommentedSodosiListUseCase: GetCommentedSodosiListUseCase,
    private val getMarkedSodosiListUseCase: GetMarkedSodosiListUseCase,
    private val changeNickNameUseCase: ChangeNickNameUseCase,
    private val patchMarkSodosiUseCase: PatchMarkSodosiUseCase,
    private val sodosiMapper: SodosiMapper,
) : BaseViewModel() {

    private val _userBaseProfile = MutableSharedFlow<Result<Pair<User, String>>>() // User, Last Visited Time
    val userBaseProfile: SharedFlow<Result<Pair<User, String>>> = _userBaseProfile.asSharedFlow()

    private val _sodosiList = MutableEventFlow<Result<List<SodosiModel>>>()
    val sodosiList: EventFlow<Result<List<SodosiModel>>> = _sodosiList.asEventFlow()

    private val _nickNameChanged = MutableEventFlow<Boolean>()
    val nickNameChanged = _nickNameChanged.asEventFlow()

    private val _unmarkEvent = MutableEventFlow<Boolean>()
    val unmarkEvent = _unmarkEvent.asEventFlow()

    init {
        getUserBaseProfile()
    }

    fun getUserBaseProfile() {
        viewModelScope.launch {
            try {
                val result = getUserInfoUseCase()
                val lastVisitedTime = getLastVisitedTimeUseCase(System.currentTimeMillis())

                when(result) {
                    is Result.Success -> _userBaseProfile.emit(Result.Success(Pair(result.data, lastVisitedTime)))
                    is Result.Error -> _userBaseProfile.emit(Result.Error(result.exception))
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getCreatedSodosiList() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getCreatedSodosiListUseCase()) {
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

    fun getCommentedSodosiList() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getCommentedSodosiListUseCase()) {
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

    fun changeNickName(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = changeNickNameUseCase(nickName)) {
                is Result.Success -> {
                    _nickNameChanged.emit(result.data)
                }
                is Result.Error -> {
                    LogUtil.e("${result.exception}")
                    _nickNameChanged.emit(false)
                }
            }
        }
    }

    fun unmarkSodosi(unmarkList: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            unmarkList.forEach {
                val result = patchMarkSodosiUseCase(it, true)
                if (result is Result.Error) {
                    _unmarkEvent.emit(false)
                    return@launch
                }
            }

            _unmarkEvent.emit(true)
        }
    }
}
