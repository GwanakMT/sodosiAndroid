package com.sodosi.ui.main

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.entity.Sodosi
import com.sodosi.domain.usecase.*
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    // 이거 그냥.. 기동할 때 리스트 주르륵 받아오면 안되나;
    // state로 분기해서 list만 주르륵 만들어놓고
    // Unit 발행해서 이벤트 발생하면 로딩 돌리면서 목록 걍 다 바꿔주기;

    private val _listUpdated = MutableStateFlow(Unit)
    val listUpdated = _listUpdated.asStateFlow()

    var mainSodosiList = emptyList<Sodosi>()
    var commentedSodosiList = emptyList<Sodosi>()
    var bookmarkSodosiList = emptyList<Sodosi>()
    var hotSodosiList = emptyList<Sodosi>()
    var newSodosiList = emptyList<Sodosi>()

    private val _bannerVisibleOrGone = MutableStateFlow(false)
    val bannerVisibleOrGone = _bannerVisibleOrGone.asStateFlow()

    fun setSodosiList() {
        viewModelScope.launch {
            mainSodosiList = getMainSodosiListUseCase()
            commentedSodosiList = getCommentedSodosiListUseCase()
            bookmarkSodosiList = getBookmarkSodosiListUseCase()
            hotSodosiList = getHotSodosiListUseCase()
            newSodosiList = getNewSodosiListUseCase()

            if (bookmarkSodosiList.isNotEmpty()) setBannerFlag()
            _listUpdated.emit(Unit)
        }
    }

    private fun setBannerFlag() {
        viewModelScope.launch {
            _bannerVisibleOrGone.value =
                getBooleanPreferencesOnceUseCase(KEY_BANNER_SHOW_FLAG) ?: true
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
