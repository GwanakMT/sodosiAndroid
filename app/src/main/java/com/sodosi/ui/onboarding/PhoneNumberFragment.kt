package com.sodosi.ui.onboarding

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentPhoneNumberBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.BaseFragment

/**
 *  PhoneNumberFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/12
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PhoneNumberFragment : BaseFragment<OnboardingViewModel, FragmentPhoneNumberBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentPhoneNumberBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        (activity as BaseActivity<*, *>).changeStatusBarColorWhite()

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
            FirebaseAuthManager.phoneNumber = phoneNumber

            if (arguments?.get("onboarding_type") == OnboardingType.SIGNUP) {
                findNavController().navigate(PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentCertificationNumber(OnboardingType.SIGNUP))
            } else {
                findNavController().navigate(PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentLoginPassword(OnboardingType.LOGIN))
            }
        }
    }

    private fun initView() {
        inputMethodManager.showSoftInput(binding.etPhoneNumber, 0)

        binding.btnNext.setStateDisable()
        binding.etPhoneNumber.addTextChangedListener {
            if ("$it".length == 0) {
                binding.etPhoneNumber.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.pretendard_regular)
            } else {
                binding.etPhoneNumber.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.pretendard_semibold)
            }

            if ("$it".length == 11) {
                binding.btnNext.setStateNormal()
            } else {
                binding.btnNext.setStateDisable()
            }
        }
    }
}
