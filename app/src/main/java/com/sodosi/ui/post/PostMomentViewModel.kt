package com.sodosi.ui.post

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.moment.PostMomentUseCase
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  PostMomentViewModel.kt
 *
 *  Created by Minji Jeong on 2022/09/11
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class PostMomentViewModel @Inject constructor(
    private val postMomentUseCase: PostMomentUseCase,
) : BaseViewModel() {

    private val _postMomentResult = MutableEventFlow<Result<Long>>()
    val postMomentResult: EventFlow<Result<Long>> = _postMomentResult.asEventFlow()

    fun postMoment(
        sodosiId: Long,
        latitude: Double,
        longitude: Double,
        roadAddress: String,
        jibunAddress: String,
        addressDetail: String,
        contents: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postMomentUseCase(
                sodosiId = sodosiId,
                latitude = latitude,
                longitude = longitude,
                roadAddress = roadAddress,
                jibunAddress = jibunAddress,
                addressDetail = addressDetail,
                contents = contents
            )

            when (result) {
                is Result.Success -> _postMomentResult.emit(Result.Success(result.data.id))
                is Result.Error -> _postMomentResult.emit(Result.Error(result.exception))
            }
        }
    }
}
