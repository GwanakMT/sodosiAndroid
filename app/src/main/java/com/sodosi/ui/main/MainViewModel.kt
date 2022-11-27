package com.sodosi.ui.main

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.entity.SodosiCategory
import com.sodosi.domain.usecase.home.GetMainSodosiListUseCase
import com.sodosi.domain.usecase.home.GetMainSuggestBannerHiddenUseCase
import com.sodosi.domain.usecase.home.SetMainSuggestBannerHiddenUseCase
import com.sodosi.domain.usecase.sodosi.GetHotSodosiListUseCase
import com.sodosi.domain.usecase.sodosi.GetNewSodosiListUseCase
import com.sodosi.domain.usecase.sodosi.PatchMarkSodosiUseCase
import com.sodosi.model.SodosiModel
import com.sodosi.model.mapper.SodosiMapper
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getMainSuggestBannerHiddenUseCase: GetMainSuggestBannerHiddenUseCase,
    private val setMainSuggestBannerHiddenUseCase: SetMainSuggestBannerHiddenUseCase,
    private val patchMarkSodosiUseCase: PatchMarkSodosiUseCase,
    private val getHotSodosiListUseCase: GetHotSodosiListUseCase,
    private val getNewSodosiListUseCase: GetNewSodosiListUseCase,
    private val sodosiMapper: SodosiMapper,
) : BaseViewModel() {
    var mainSodosiList = emptyList<SodosiModel>()
    var commentedSodosiList = emptyList<SodosiModel>()
    var bookmarkSodosiList = emptyList<SodosiModel>()
    var hotSodosiList = emptyList<SodosiModel>()
    var newSodosiList = emptyList<SodosiModel>()

    private val _sodosiListsUpdatedEvent = MutableEventFlow<Boolean>()
    val sodosiListsUpdatedEvent = _sodosiListsUpdatedEvent.asEventFlow()

    private val _hotSodosiListUpdatedEvent = MutableEventFlow<Unit>()
    val hotSodosiListUpdatedEvent = _hotSodosiListUpdatedEvent.asEventFlow()

    private val _newSodosiListUpdatedEvent = MutableEventFlow<Unit>()
    val newSodosiListUpdatedEvent = _newSodosiListUpdatedEvent.asEventFlow()

    private val _patchMarkSodosiEvent = MutableEventFlow<Boolean>()
    val patchMarkSodosiEvent = _patchMarkSodosiEvent.asEventFlow()

    private val _showSuggestBanner = MutableStateFlow(false)
    val showSuggestBanner = _showSuggestBanner.asStateFlow()

    init {
        getMainSodosiList()
    }

    fun getMainSodosiList() {
        viewModelScope.launch {
            getHotSodosiList() // HOT한 소도시는 따로 조회
            geNewtSodosiList() // NEW 소도시도 따로 조회

            when(val result = getMainSodosiListUseCase()) {
                is Result.Success -> {
                    val sodosiListMap = result.data.second
                    mainSodosiList = sodosiListMap[SodosiCategory.MAIN_BANNER]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    commentedSodosiList = sodosiListMap[SodosiCategory.COMMENTED]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    bookmarkSodosiList = sodosiListMap[SodosiCategory.MARKED]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()

                    val hasSodosi = result.data.first
                    val suggestBannerHidden = getMainSuggestBannerHiddenUseCase()
                    _showSuggestBanner.emit(!hasSodosi && !suggestBannerHidden)
                    _sodosiListsUpdatedEvent.emit(true)
                }

                is Result.Error -> {
                    _sodosiListsUpdatedEvent.emit(false)
                }
            }
        }
    }

    fun setSuggestBannerHide() {
        viewModelScope.launch {
            setMainSuggestBannerHiddenUseCase()
        }
    }

    fun patchMarkSodosi(id: Long, isMarkedCurrent: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (patchMarkSodosiUseCase(id, isMarkedCurrent)) {
                is Result.Success -> {
                    _patchMarkSodosiEvent.emit(true)
                }

                is Result.Error -> {
                    _patchMarkSodosiEvent.emit(false)
                }
            }
        }
    }

    private fun getHotSodosiList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getHotSodosiListUseCase()
            if (result is Result.Success) {
                hotSodosiList = result.data.map { sodosiMapper.mapToModel(it) }
                _hotSodosiListUpdatedEvent.emit(Unit)
            }
        }
    }

    private fun geNewtSodosiList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNewSodosiListUseCase()
            if (result is Result.Success) {
                newSodosiList = result.data.map { sodosiMapper.mapToModel(it) }
                _newSodosiListUpdatedEvent.emit(Unit)
            }
        }
    }

    companion object {
        private const val KEY_BANNER_SHOW_FLAG = "KEY_BANNER_SHOW_FLAG"
    }
}
