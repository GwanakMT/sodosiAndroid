package com.sodosi.ui.onboarding

import android.app.Activity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.databinding.FragmentStep2Binding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  Step2Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step2Fragment : BaseFragment<OnboardingViewModel, FragmentStep2Binding>() {
    private lateinit var authManager: FirebaseAuthManager

    override fun getViewBinding() = FragmentStep2Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        authManager = FirebaseAuthManager(activity as Activity)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (true) { // 이미 가입된 번호라면
                findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep6())
            } else {
                val phoneNumber = "+82${binding.etPhoneNumber.text.toString().toInt()}"
                authManager.verifyPhoneNumber(phoneNumber)
                findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep3())
            }
        }
    }
}
