package com.github.sookhee.sodosi.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentStep5Binding

/**
 *  Step5Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step5Fragment : BaseFragment<OnboardingViewModel, FragmentStep5Binding>() {
    override fun getViewBinding() = FragmentStep5Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(Step5FragmentDirections.actionFragmentStep5ToFragmentStep6())
        }
    }

    override fun observeData() {

    }
}
