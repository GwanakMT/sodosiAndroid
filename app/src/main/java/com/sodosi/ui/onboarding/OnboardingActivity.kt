package com.sodosi.ui.onboarding

import androidx.activity.viewModels
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.databinding.ActivityOnboardingBinding

/**
 *  OnboardingActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class OnboardingActivity : BaseActivity<OnboardingViewModel, ActivityOnboardingBinding>() {
    override fun getViewBinding() = ActivityOnboardingBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {

    }

    override fun observeData() {

    }
}
