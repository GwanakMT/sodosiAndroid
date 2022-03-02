package com.sodosi.ui.onboarding

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.databinding.FragmentStep3Binding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  Step3Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step3Fragment : BaseFragment<OnboardingViewModel, FragmentStep3Binding>(), FirebaseAuthManager.VerificationPhoneListener {
    private lateinit var authManager: FirebaseAuthManager

    override fun getViewBinding() = FragmentStep3Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        authManager = FirebaseAuthManager(activity as Activity)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val smsCode = binding.etSmsCode.text.toString()
            authManager.signInWithPhoneAuthCredential(smsCode, this@Step3Fragment)
        }

        binding.tvSmsCodeResend.setOnClickListener {
            authManager.verifyPhoneNumber()
        }
    }

    override fun observeData() {

    }

    override fun onAuthSuccess() {
        Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
        findNavController().navigate(Step3FragmentDirections.actionFragmentStep3ToFragmentStep4())
    }

    override fun onAuthFail() {
        Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show()
    }
}
