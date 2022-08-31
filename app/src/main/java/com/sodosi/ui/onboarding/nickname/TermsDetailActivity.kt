package com.sodosi.ui.onboarding.nickname

import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityTermsDetailBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  TermsDetailActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class TermsDetailActivity : BaseActivity<OnboardingViewModel, ActivityTermsDetailBinding>() {
    override fun getViewBinding() = ActivityTermsDetailBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }
    }
}
