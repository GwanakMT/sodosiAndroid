package com.sodosi.ui.list

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.sodosi.GetAllSodosiListUseCase
import com.sodosi.domain.usecase.sodosi.PatchMarkSodosiUseCase
import com.sodosi.model.SodosiModel
import com.sodosi.model.mapper.SodosiMapper
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  SodosiListViewModel.kt
 *
 *  Created by Minji Jeong on 2022/09/09
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class SodosiListViewModel @Inject constructor(
    private val getAllSodosiUseCase: GetAllSodosiListUseCase,
    private val patchMarkSodosiUseCase: PatchMarkSodosiUseCase,
    private val sodosiMapper: SodosiMapper,
) : BaseViewModel() {
    var sodosiListSortByRecent: List<SodosiModel>? = null
    var sodosiListSortByPopular: List<SodosiModel>? = null

    private val inquiryAllSodosiListSortByRecent = MutableEventFlow<Unit>()
    private val inquiryAllSodosiListSortByPopular = MutableEventFlow<Unit>()

    val inquirySodosiListSuccessEvent = inquiryAllSodosiListSortByRecent.combine(inquiryAllSodosiListSortByPopular) { _, _ -> }
    val inquirySodosiListErrorEvent = MutableEventFlow<String>()

    private val _currentTab = MutableStateFlow(SORT_BY_POPULAR)
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    private val _updateMarkStateEvent = MutableEventFlow<Result<Unit>>()
    val updateMarkStateEvent: EventFlow<Result<Unit>> = _updateMarkStateEvent.asEventFlow()

    init {
        getSodosiListSortByRecent()
        getSodosiListSortByPopular()
    }

    private fun getSodosiListSortByRecent() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getAllSodosiUseCase(SORT_BY_RECENT)) {
                is Result.Success -> {
                    sodosiListSortByRecent = result.data.map { sodosiMapper.mapToModel(it) }
                    inquiryAllSodosiListSortByRecent.emit(Unit)
                }
                is Result.Error -> inquirySodosiListErrorEvent.emit(result.exception.message.toString())
            }
        }
    }

    private fun getSodosiListSortByPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getAllSodosiUseCase(SORT_BY_POPULAR)) {
                is Result.Success -> {
                    sodosiListSortByPopular = result.data.map { sodosiMapper.mapToModel(it) }
                    inquiryAllSodosiListSortByPopular.emit(Unit)
                }
                is Result.Error -> inquirySodosiListErrorEvent.emit(result.exception.message.toString())
            }
        }
    }

    fun setCurrentTab(sortBy: String) {
        viewModelScope.launch {
            when (sortBy) {
                SORT_BY_RECENT -> _currentTab.value = SORT_BY_RECENT
                SORT_BY_POPULAR -> _currentTab.value = SORT_BY_POPULAR
            }
        }
    }

    fun patchMarkSodosi(id: Long, isMarkedCurrent: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = patchMarkSodosiUseCase(id, isMarkedCurrent)) {
                is Result.Success -> {
                    sodosiListSortByPopular = sodosiListSortByPopular?.map { if (it.id == id) it.copy(isMarked = result.data) else it }
                    sodosiListSortByRecent = sodosiListSortByRecent?.map { if (it.id == id) it.copy(isMarked = result.data) else it }
                    _updateMarkStateEvent.emit(Result.Success(Unit))
                }

                is Result.Error -> {
                    _updateMarkStateEvent.emit(Result.Error(result.exception))
                }
            }
        }
    }

    companion object {
        const val SORT_BY_RECENT = "RECENT"
        const val SORT_BY_POPULAR = "POPULAR"
    }
}
