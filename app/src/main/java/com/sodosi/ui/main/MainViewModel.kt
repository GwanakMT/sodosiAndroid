package com.sodosi.ui.main

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.entity.SodosiCategory
import com.sodosi.domain.usecase.home.GetMainSodosiListUseCase
import com.sodosi.domain.usecase.home.GetMainSuggestBannerHiddenUseCase
import com.sodosi.domain.usecase.home.SetMainSuggestBannerHiddenUseCase
import com.sodosi.model.SodosiModel
import com.sodosi.model.mapper.SodosiMapper
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
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
    private val getMainSuggestBannerHiddenUseCase: GetMainSuggestBannerHiddenUseCase,
    private val setMainSuggestBannerHiddenUseCase: SetMainSuggestBannerHiddenUseCase,
    private val sodosiMapper: SodosiMapper,
) : BaseViewModel() {
    var mainSodosiList = emptyList<SodosiModel>()
    var commentedSodosiList = emptyList<SodosiModel>()
    var bookmarkSodosiList = emptyList<SodosiModel>()
    var hotSodosiList = emptyList<SodosiModel>()
    var newSodosiList = emptyList<SodosiModel>()

    private val _sodosiListsUpdatedEvent = MutableEventFlow<Boolean>()
    val sodosiListsUpdatedEvent = _sodosiListsUpdatedEvent.asEventFlow()

    private val _showSuggestBanner = MutableStateFlow(false)
    val showSuggestBanner = _showSuggestBanner.asStateFlow()

    init {
        getMainSodosiList()
    }

    private fun getMainSodosiList() {
        viewModelScope.launch {
            when(val result = getMainSodosiListUseCase()) {
                is Result.Success -> {
                    val sodosiListMap = result.data.second
                    mainSodosiList = sodosiListMap[SodosiCategory.MAIN_BANNER]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    commentedSodosiList = sodosiListMap[SodosiCategory.COMMENTED]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    bookmarkSodosiList = sodosiListMap[SodosiCategory.MARKED]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    hotSodosiList = sodosiListMap[SodosiCategory.HOT]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()
                    newSodosiList = sodosiListMap[SodosiCategory.NEW]?.map { sodosiMapper.mapToModel(it) } ?: emptyList()

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

    companion object {
        private const val KEY_BANNER_SHOW_FLAG = "KEY_BANNER_SHOW_FLAG"
    }
}
