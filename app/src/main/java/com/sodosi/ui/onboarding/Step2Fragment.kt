package com.sodosi.ui.onboarding

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.databinding.FragmentStep2Binding
import com.sodosi.ui.common.base.BaseActivity
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
        (activity as BaseActivity<*, *>).changeStatusBarColorWhite()

        authManager = FirebaseAuthManager(activity as Activity)

        initButton()
        initKeyboard()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (true) { // 이미 가입된 번호라면
                findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep3())
            } else {
                val phoneNumber = "+82${binding.etPhoneNumber.text.toString().toInt()}"
                authManager.verifyPhoneNumber(phoneNumber)
                findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep3())
            }
        }
    }

    private fun initButton() {
        binding.btnNext.setStateDisable()
        binding.etPhoneNumber.addTextChangedListener {
            if ("$it".length == 11) {
                binding.btnNext.setStateNormal()
            } else {
                binding.btnNext.setStateDisable()
            }
        }
    }

    private fun initKeyboard() {
        val inputMethodManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etPhoneNumber, 0)
    }
}
