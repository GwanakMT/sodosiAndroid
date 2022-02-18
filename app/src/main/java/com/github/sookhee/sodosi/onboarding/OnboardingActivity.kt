package com.github.sookhee.sodosi.onboarding

import androidx.activity.viewModels
import com.github.sookhee.sodosi.common.base.BaseActivity
import com.github.sookhee.sodosi.databinding.ActivityOnboardingBinding

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
