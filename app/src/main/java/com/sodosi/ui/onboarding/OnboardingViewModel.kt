package com.sodosi.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.entity.Terms
import com.sodosi.domain.usecase.user.*
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class OnboardingViewModel @Inject constructor(
    private val checkPhoneNumberUseCase: CheckPhoneNumberUseCase,
    private val getUserPrivacyPolicyContentsUseCase: GetUserPrivacyPolicyContentsUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val signInWithoutPasswordUseCase: SignInWithoutPasswordUseCase,
) : BaseViewModel() {
    private val _timer = MutableStateFlow(MINUTE_3)
    val timer: StateFlow<Int> = _timer

    private val _userNotJoined = MutableEventFlow<Boolean>()
    val userNotJoined: EventFlow<Boolean> = _userNotJoined.asEventFlow()

    private val _userPrivacyPolicyTerms = MutableEventFlow<List<Terms>>()
    val userPrivacyPolicyTerms = _userPrivacyPolicyTerms.asEventFlow()

    private var agreeInfoMap = mapOf<String, Boolean>()

    private val _isLoginSuccess = MutableEventFlow<Boolean>()
    val isLoginSuccess: EventFlow<Boolean> = _isLoginSuccess.asEventFlow()

    private val _isSignUpSuccess = MutableEventFlow<Pair<Boolean, String>>()
    val isSignUpSuccess: EventFlow<Pair<Boolean, String>> = _isSignUpSuccess.asEventFlow()

    fun resetTimer() {
        _timer.value = MINUTE_3
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timer.value > 0) {
                delay(1000)
                _timer.value = _timer.value - 1
            }
        }
    }

    fun checkUserNotJoined(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = checkPhoneNumberUseCase(phoneNumber)
            _userNotJoined.emit(result)
        }
    }

    fun getTerms() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getUserPrivacyPolicyContentsUseCase()) {
                is Result.Success -> _userPrivacyPolicyTerms.emit(result.data)
                is Result.Error -> _userPrivacyPolicyTerms.emit(emptyList())
            }
        }
    }

    fun setAllowTerms(list: List<Terms>) {
        val newMap = HashMap<String, Boolean>()
        list.forEach {
            newMap[it.id.toString()] = it.isAllow
        }

        agreeInfoMap = newMap
    }

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(signInUseCase(phoneNumber, password)) {
                is Result.Success -> _isLoginSuccess.emit(true)
                is Result.Error -> _isLoginSuccess.emit(false)
            }
        }
    }

    fun loginWithoutPassword(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(signInWithoutPasswordUseCase(phoneNumber)) {
                is Result.Success -> _isLoginSuccess.emit(true)
                is Result.Error -> _isLoginSuccess.emit(false)
            }
        }
    }

    fun signUp(phoneNumber: String, nickName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUseCase(
                phoneNumber = phoneNumber,
                password = password,
                name = "-",
                nickName = nickName,
                agreeInfoMap = agreeInfoMap
            )

            _isSignUpSuccess.emit(result)
        }
    }

    companion object {
        private const val MINUTE_3 = 180
    }
}
