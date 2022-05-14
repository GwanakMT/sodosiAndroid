package com.sodosi.ui.main

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.usecase.*
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  MainViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMainSodosiListUseCase: GetMainSodosiListUseCase,
    private val getCommentedSodosiListUseCase: GetCommentedSodosiListUseCase,
    private val getBookmarkSodosiListUseCase: GetBookmarkSodosiListUseCase,
    private val getHotSodosiListUseCase: GetHotSodosiListUseCase,
    private val getNewSodosiListUseCase: GetNewSodosiListUseCase,
    private val setBooleanPreferencesUseCase: SetBooleanPreferencesUseCase,
    private val getBooleanPreferencesOnceUseCase: GetBooleanPreferencesOnceUseCase,
) : BaseViewModel() {
    private val _mainSodosiList = MutableStateFlow<List<Sodosi>>(listOf())
    val mainSodosiList: StateFlow<List<Sodosi>> = _mainSodosiList

    private val _commentedSodosiList = MutableStateFlow<List<Sodosi>>(listOf())
    val commentedSodosiList: StateFlow<List<Sodosi>> = _commentedSodosiList

    private val _bookmarkSodosiList = MutableStateFlow<List<Sodosi>>(listOf())
    val bookmarkSodosiList: StateFlow<List<Sodosi>> = _bookmarkSodosiList

    private val _hotSodosiList = MutableStateFlow<List<Sodosi>>(listOf())
    val hotSodosiList: StateFlow<List<Sodosi>> = _hotSodosiList

    private val _newSodosiList = MutableStateFlow<List<Sodosi>>(listOf())
    val newSodosiList: StateFlow<List<Sodosi>> = _newSodosiList

    private val _bannerIsShow = MutableStateFlow(false)
    val bannerIsShow = _bannerIsShow.combine(_bookmarkSodosiList) { bannerFlag, sodosiList ->
        bannerFlag && sodosiList.isEmpty()
    }

    fun getMainSodosiList() {
        viewModelScope.launch {
            _mainSodosiList.value = getMainSodosiListUseCase()
        }
    }

    fun getCommentedSodosiList() {
        viewModelScope.launch {
            _commentedSodosiList.value = getCommentedSodosiListUseCase()
        }
    }

    fun getBookmarkSodosiList() {
        viewModelScope.launch {
            _bookmarkSodosiList.value = getBookmarkSodosiListUseCase()
        }
    }

    fun getHotSodosiList() {
        viewModelScope.launch {
            _hotSodosiList.value = getHotSodosiListUseCase()
        }
    }

    fun getNewSodosiList() {
        viewModelScope.launch {
            _newSodosiList.value = getNewSodosiListUseCase()

        }
    }

    fun getBannerShowFlag() {
        viewModelScope.launch {
            _bannerIsShow.value = getBooleanPreferencesOnceUseCase(KEY_BANNER_SHOW_FLAG) ?: true
        }
    }

    fun setBannerShowFlagFalse() {
        viewModelScope.launch {
            setBooleanPreferencesUseCase.invoke(KEY_BANNER_SHOW_FLAG, false)
        }
    }

    companion object {
        private const val KEY_BANNER_SHOW_FLAG = "KEY_BANNER_SHOW_FLAG"
    }
}
