package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sodosi.R
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentStep1Binding

/**
 *  Step1Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step1Fragment : BaseFragment<OnboardingViewModel, FragmentStep1Binding>() {
    override fun getViewBinding() = FragmentStep1Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        binding.btnOnBoardingStart.setOnClickListener {
            findNavController().navigate(Step1FragmentDirections.actionFragmentStep1ToFragmentStep2())
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
