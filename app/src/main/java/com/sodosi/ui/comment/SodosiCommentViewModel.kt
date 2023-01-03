package com.sodosi.ui.comment

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.moment.ReportMomentUseCase
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  SodosiCommentViewModel.kt
 *
 *  Created by Minji Jeong on 2022/05/22
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class SodosiCommentViewModel @Inject constructor(
    private val reportMomentUseCase: ReportMomentUseCase,
): BaseViewModel() {

    private val _reportResult = MutableEventFlow<Result<Unit>>()
    val reportResult: EventFlow<Result<Unit>> = _reportResult.asEventFlow()

    fun reportMoment(sodosiId: Long, momentId: Long, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reportMomentUseCase(sodosiId, momentId, reason)
            _reportResult.emit(result)
        }
    }
}