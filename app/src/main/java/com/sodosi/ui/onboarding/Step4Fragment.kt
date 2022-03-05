package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
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
        initAppbar()
        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                activity?.onBackPressed()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(Step4FragmentDirections.actionFragmentStep4ToFragmentStep5())
        }
    }
}
