package com.sodosi.ui.create

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.sodosi.CreateSodosiUseCase
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  CreateSodosiViewModel.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class CreateSodosiViewModel @Inject constructor(
    private val createSodosiUseCase: CreateSodosiUseCase,
) : BaseViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _createSodosiResult: MutableEventFlow<Pair<Result<Long>, Boolean?>> = MutableEventFlow()
    val createSodosiResult: EventFlow<Pair<Result<Long>, Boolean?>> = _createSodosiResult.asEventFlow()

    fun createSodosi(name: String, icon: String, viewState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val result = createSodosiUseCase(name, icon, viewState)
            _isLoading.value = false

            when(result) {
                is Result.Success -> {
                    val createSodosiBefore = false
                    _createSodosiResult.emit(Pair(result, createSodosiBefore))
                }

                is Result.Error -> {
                    _createSodosiResult.emit(Pair(result, null))
                }
            }
        }
    }

    private fun checkCreateSodosiFirst() {

    }
}
