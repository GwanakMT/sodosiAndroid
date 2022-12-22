package com.sodosi.ui.setting

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.user.*
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  SettingViewModel.kt
 *
 *  Created by Minji Jeong on 2022/09/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val setPhoneNumberUseCase: SetPhoneNumberUseCase,
    private val checkCurrentPasswordUseCase: CheckCurrentPasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val changePhoneNumberUseCase: ChangePhoneNumberUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    ) : BaseViewModel() {
    private val _timer = MutableStateFlow(MINUTE_3)
    val timer: StateFlow<Int> = _timer

    private val _phoneNumber: MutableEventFlow<String> = MutableEventFlow()
    val phoneNumber: EventFlow<String> = _phoneNumber.asEventFlow()

    private val _changePhoneNumberEvent = MutableEventFlow<Boolean>()
    val changePhoneNumberEvent = _changePhoneNumberEvent.asEventFlow()

    private val _changePasswordEvent = MutableEventFlow<Int>()
    val changePasswordEvent = _changePasswordEvent.asEventFlow()

    private val _deleteUserEvent: MutableEventFlow<Boolean> = MutableEventFlow()
    val deleteUserEvent = _deleteUserEvent.asEventFlow()

    init {
        getPhoneNumber()
    }

    fun getPhoneNumber() {
        viewModelScope.launch {
            _phoneNumber.emit(getPhoneNumberUseCase())
        }
    }

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

    fun changePhoneNumber(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = changePhoneNumberUseCase(phoneNumber)
            if (result is Result.Success) {
                // 성공
                setPhoneNumberUseCase(phoneNumber)
                _changePhoneNumberEvent.emit(true)
            } else {
                // 통신 실패
                _changePhoneNumberEvent.emit(false)
            }
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkPasswordResult = checkCurrentPasswordUseCase(currentPassword)
            if (checkPasswordResult !is Result.Success || !checkPasswordResult.data) {
                // 현재 비밀번호 일치X
                _changePasswordEvent.emit(2)
            } else {
                val changePasswordResult = changePasswordUseCase(newPassword)
                if (changePasswordResult is Result.Success && changePasswordResult.data) {
                    // 성공
                    _changePasswordEvent.emit(1)
                } else {
                    // 통신 실패
                    _changePasswordEvent.emit(3)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            when(deleteUserUseCase()) {
                is Result.Success -> _deleteUserEvent.emit(true)
                is Result.Error -> _deleteUserEvent.emit(false)
            }
        }
    }

    companion object {
        private const val MINUTE_3 = 180
    }
}
