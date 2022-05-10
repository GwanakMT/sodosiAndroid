package com.sodosi.ui.main

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.usecase.*
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
}
