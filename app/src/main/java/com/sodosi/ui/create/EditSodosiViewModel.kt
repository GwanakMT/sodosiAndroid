package com.sodosi.ui.create

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.sodosi.PatchSodosiUseCase
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  EditSodosiViewModel.kt
 *
 *  Created by Minji Jeong on 2022/10/29
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class EditSodosiViewModel @Inject constructor(
    private val patchSodosiUseCase: PatchSodosiUseCase,
) : BaseViewModel() {
    private val _patchSodosiResult: MutableEventFlow<Boolean> = MutableEventFlow()
    val patchSodosiResult: EventFlow<Boolean> = _patchSodosiResult.asEventFlow()

    fun patchSodosi(id: Long, name: String, icon: String, viewState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = patchSodosiUseCase(id, name, icon, viewState)

            when(result) {
                is Result.Success -> {
                    _patchSodosiResult.emit(true)
                }

                is Result.Error -> {
                    _patchSodosiResult.emit(false)
                }
            }
        }
    }
}
