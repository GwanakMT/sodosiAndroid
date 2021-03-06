package com.sodosi.ui.onboarding.welcome

import android.content.Intent
import androidx.fragment.app.viewModels
import com.sodosi.R
import com.sodosi.databinding.FragmentWelcomeBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.main.MainActivity
import com.sodosi.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  WelcomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class WelcomeFragment : BaseFragment<OnboardingViewModel, FragmentWelcomeBinding>() {
    override fun getViewBinding() = FragmentWelcomeBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews(): Unit = with(binding) {
        val nickname = arguments?.getString("nickname") ?: return
        binding.tvWelcome.text = getString(R.string.onboarding_welcome, nickname)

        CoroutineScope(Dispatchers.Main).launch {
            delay(WELCOME_SCREEN_DELAY)

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }
    }

    override fun observeData() {

    }

    companion object {
        private const val WELCOME_SCREEN_DELAY = 2000L
    }
}
