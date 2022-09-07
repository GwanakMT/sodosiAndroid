package com.sodosi.ui.setting

import androidx.lifecycle.viewModelScope
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
) : BaseViewModel() {

    private val _phoneNumber: MutableEventFlow<String> = MutableEventFlow()
    val phoneNumber: EventFlow<String> = _phoneNumber.asEventFlow()

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
}
