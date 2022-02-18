package com.github.sookhee.sodosi.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentStep3Binding

/**
 *  Step3Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step3Fragment : BaseFragment<OnboardingViewModel, FragmentStep3Binding>() {
    override fun getViewBinding() = FragmentStep3Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(Step3FragmentDirections.actionFragmentStep3ToFragmentStep4())
        }
    }

    override fun observeData() {

    }
}
