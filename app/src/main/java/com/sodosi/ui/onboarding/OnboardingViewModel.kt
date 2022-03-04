package com.sodosi.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.sodosi.domain.usecase.GetAlbumUseCase
import com.sodosi.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getAlbumUseCase: GetAlbumUseCase
) : BaseViewModel() {
    private val _album = MutableStateFlow("")
    val album: StateFlow<String> = _album

    fun getAlbum(id: Int) {
        viewModelScope.launch {
            _album.value = getAlbumUseCase(2)
        }
    }
}
