package com.sodosi.ui.splash

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.usecase.token.GetTokenUseCase
import com.sodosi.domain.usecase.user.SetLastVisitedTimeUseCase
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  IntroViewModel.kt
 *
 *  Created by Minji Jeong on 2022/09/06
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val setLastVisitedTimeUseCase: SetLastVisitedTimeUseCase,
): BaseViewModel() {

    fun checkHasToken(): Boolean {
        return getTokenUseCase().isNotBlank()
    }

    fun setVisitedTime(currentTimeMillis: Long) {
        viewModelScope.launch {
            setLastVisitedTimeUseCase(currentTimeMillis)
        }
    }
}