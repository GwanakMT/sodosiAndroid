package com.github.sookhee.sodosi.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentStep1Binding

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
    }

    override fun observeData() {

    }
}
