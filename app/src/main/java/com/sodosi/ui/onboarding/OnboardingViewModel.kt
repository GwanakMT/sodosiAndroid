package com.sodosi.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  OnboardingViewModel.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BaseViewModel() {
    private val _timer = MutableStateFlow(MINUTE_3)
    val timer: StateFlow<Int> = _timer

    private val _isNicknamePossible = MutableStateFlow<Boolean?>(null)
    val isNicknamePossible: StateFlow<Boolean?> = _isNicknamePossible

    private val _isLoginSuccess = MutableStateFlow<Boolean?>(null)
    val isLoginSuccess: StateFlow<Boolean?> = _isLoginSuccess

    fun startTimer() {
        viewModelScope.launch {
            while (_timer.value > 0) {
                delay(1000)
                _timer.value = _timer.value - 1
            }
        }
    }

    fun resetTimer() {
        _timer.value = MINUTE_3
        startTimer()
    }

    fun checkNickname(nickname: String) {
        _isNicknamePossible.value = true
    }

    fun login() {
        _isLoginSuccess.value = true
    }

    companion object {
        private const val MINUTE_3 = 180
    }
}
