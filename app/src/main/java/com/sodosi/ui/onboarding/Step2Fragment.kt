package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentStep2Binding

/**
 *  Step2Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step2Fragment : BaseFragment<OnboardingViewModel, FragmentStep2Binding>() {
    override fun getViewBinding() = FragmentStep2Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnFindLocation.setOnClickListener {
            findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep3())
        }
    }

    override fun observeData() {

    }
}
