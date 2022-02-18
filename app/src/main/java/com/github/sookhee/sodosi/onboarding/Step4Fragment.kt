package com.github.sookhee.sodosi.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentStep4Binding

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
