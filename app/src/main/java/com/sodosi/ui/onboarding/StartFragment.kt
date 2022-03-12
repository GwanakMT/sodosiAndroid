package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sodosi.R
import com.sodosi.databinding.FragmentStartBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.base.BaseActivity

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
            findNavController().navigate(Step1FragmentDirections.actionFragmentStep1ToFragmentStep2())
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(Step1FragmentDirections.actionFragmentStep1ToFragment6())
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
