package com.sodosi.ui.onboarding

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sodosi.R
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *  OnboardingActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<OnboardingViewModel, ActivityOnboardingBinding>() {
    override fun getViewBinding() = ActivityOnboardingBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {

    }

    override fun observeData() {

    }
}
