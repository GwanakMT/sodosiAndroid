package com.sodosi.ui.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentLoginPasswordBinding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  SignPasswordFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class LoginPasswordFragment : BaseFragment<OnboardingViewModel, FragmentLoginPasswordBinding>() {
    override fun getViewBinding() = FragmentLoginPasswordBinding.inflate(layoutInflater)

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
