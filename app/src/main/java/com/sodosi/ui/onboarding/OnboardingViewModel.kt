package com.sodosi.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.entity.Terms
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _isNicknamePossible = MutableSharedFlow<Boolean>()
    val isNicknamePossible: SharedFlow<Boolean> = _isNicknamePossible

    private val _isLoginSuccess = MutableStateFlow<Boolean?>(null)
    val isLoginSuccess: StateFlow<Boolean?> = _isLoginSuccess

    private val _isSignSuccess = MutableStateFlow<Boolean?>(null)
    val isSignSuccess: StateFlow<Boolean?> = _isSignSuccess

    private fun startTimer() {
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

    fun getTerms(): List<Terms> {
        return listOf(
            Terms(1, "[필수] 서비스 이용약관 동의", "", false),
            Terms(2, "[필수] 개인정보 처리방침 동의", "", false)
        )
    }

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            _isNicknamePossible.emit(true)
        }
    }

    fun login() {
        _isLoginSuccess.value = true
    }

    fun singIn() {
        _isSignSuccess.value = true
    }

    companion object {
        private const val MINUTE_3 = 180
    }
}
