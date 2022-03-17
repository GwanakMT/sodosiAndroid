package com.sodosi.ui.onboarding.start

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sodosi.R
import com.sodosi.databinding.FragmentStartBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.onboarding.OnboardingType
import com.sodosi.ui.onboarding.OnboardingViewModel

/**
 *  StartFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class StartFragment : BaseFragment<OnboardingViewModel, FragmentStartBinding>() {
    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        (activity as BaseActivity<*, *>).changeStatusBarColorBlack()

        binding.btnOnBoardingStart.setOnClickListener {
            navigate(R.id.fragment_start) {
                findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentPhoneNumber(
                    OnboardingType.SIGNUP
                ))
            }
        }

        binding.btnLogin.setOnClickListener {
            navigate(R.id.fragment_start) {
                findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentPhoneNumber(
                    OnboardingType.LOGIN
                ))
            }
        }

        initOnboardingGif()
    }

    override fun observeData() {

    }

    private fun initOnboardingGif() {
        Glide.with(this)
            .load(R.raw.onboarding)
            .into(binding.ivOnboarding)
    }
}
