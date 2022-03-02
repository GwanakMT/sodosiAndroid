package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.databinding.FragmentStep4Binding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  Step4Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step4Fragment : BaseFragment<OnboardingViewModel, FragmentStep4Binding>() {
    override fun getViewBinding() = FragmentStep4Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(Step4FragmentDirections.actionFragmentStep4ToFragmentStep5())
        }
    }

    override fun observeData() {

    }
}
