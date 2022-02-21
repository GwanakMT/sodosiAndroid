package com.sodosi.ui.onboarding

import android.app.Activity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentStep3Binding

/**
 *  Step3Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step3Fragment : BaseFragment<OnboardingViewModel, FragmentStep3Binding>() {
    private lateinit var authManager: FirebaseAuthManager

    override fun getViewBinding() = FragmentStep3Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        authManager = FirebaseAuthManager(activity as Activity)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val phoneNumber = "+82${binding.etPhoneNumber.text.toString().toInt()}"
            authManager.verifyPhoneNumber(phoneNumber)
            findNavController().navigate(Step3FragmentDirections.actionFragmentStep3ToFragmentStep4())
        }
    }
}
