package com.sodosi.ui.setting

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.Result
import com.sodosi.domain.usecase.user.DeleteUserUseCase
import com.sodosi.domain.usecase.user.GetPhoneNumberUseCase
import com.sodosi.domain.usecase.user.LogoutUseCase
import com.sodosi.ui.common.base.BaseViewModel
import com.sodosi.ui.common.base.EventFlow
import com.sodosi.ui.common.base.MutableEventFlow
import com.sodosi.ui.common.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    ) : BaseViewModel() {

    private val _phoneNumber: MutableEventFlow<String> = MutableEventFlow()
    val phoneNumber: EventFlow<String> = _phoneNumber.asEventFlow()

    private val _deleteUserEvent: MutableEventFlow<Boolean> = MutableEventFlow()
    val deleteUserEvent = _deleteUserEvent.asEventFlow()

    init {
        getPhoneNumber()
    }

    private fun getPhoneNumber() {
        viewModelScope.launch {
            _phoneNumber.emit(getPhoneNumberUseCase())
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
}
