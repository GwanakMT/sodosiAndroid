package com.sodosi.ui.onboarding

import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityOnboardingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.onboarding.start.StartFragment
import com.sodosi.ui.onboarding.welcome.WelcomeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 *  OnboardingActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<OnboardingViewModel, ActivityOnboardingBinding>() {
    private var backPressWaitTime = 0L

    override fun getViewBinding() = ActivityOnboardingBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {

    }

    override fun observeData() {

    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        if (navHostFragment?.childFragmentManager?.fragments?.get(0) is StartFragment) {
            when {
                System.currentTimeMillis() - backPressWaitTime >= BACKPRESS_DELAY_TIME -> {
                    backPressWaitTime = System.currentTimeMillis()
                    SodosiToast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    super.onBackPressed()
                }
            }
        } else if (navHostFragment?.childFragmentManager?.fragments?.get(0) !is WelcomeFragment) {
            super.onBackPressed()
        }
    }

    companion object {
        private const val BACKPRESS_DELAY_TIME = 1500
    }
}
