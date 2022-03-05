package com.sodosi.ui.onboarding

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
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
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentStep2Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        (activity as BaseActivity<*, *>).changeStatusBarColorWhite()

        authManager = FirebaseAuthManager(activity as Activity)

        initAppbar()
        initView()

        setOnClickListener()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                inputMethodManager.hideSoftInputFromWindow(binding.etPhoneNumber.windowToken, 0)
                activity?.onBackPressed()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            val phoneNumber = "+82${binding.etPhoneNumber.text.toString().toInt()}"
            authManager.verifyPhoneNumber(phoneNumber)
            findNavController().navigate(Step2FragmentDirections.actionFragmentStep2ToFragmentStep3())
        }
    }

    private fun initView() {
        inputMethodManager.showSoftInput(binding.etPhoneNumber, 0)

        binding.btnNext.setStateDisable()
        binding.etPhoneNumber.addTextChangedListener {
            if ("$it".length == 0) {
                binding.etPhoneNumber.typeface =
                    ResourcesCompat.getFont(context!!, R.font.pretendard_regular)
            } else {
                binding.etPhoneNumber.typeface =
                    ResourcesCompat.getFont(context!!, R.font.pretendard_semibold)
            }

            if ("$it".length == 11) {
                binding.btnNext.setStateNormal()
            } else {
                binding.btnNext.setStateDisable()
            }
        }
    }
}
