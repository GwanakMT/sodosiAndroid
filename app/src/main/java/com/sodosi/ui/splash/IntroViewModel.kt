package com.sodosi.ui.splash

import com.sodosi.domain.usecase.token.GetTokenUseCase
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
): BaseViewModel() {

    fun checkHasToken(): Boolean {
        return getTokenUseCase().isNotBlank()
    }
}