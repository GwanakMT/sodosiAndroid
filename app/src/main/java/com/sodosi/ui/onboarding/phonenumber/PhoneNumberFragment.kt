package com.sodosi.ui.onboarding.phonenumber

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentPhoneNumberBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.onboarding.OnboardingType
import com.sodosi.ui.onboarding.OnboardingViewModel
import com.sodosi.ui.onboarding.certification.FirebaseAuthManager
import java.util.regex.Pattern

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
            val phoneNumber = binding.etPhoneNumber.text.toString()
            if (checkPhoneRegex(phoneNumber)) {
                FirebaseAuthManager.phoneNumber = "+82${phoneNumber.toInt()}"

                if (arguments?.get("onboarding_type") == OnboardingType.SIGNUP) {
                    navigate(R.id.fragment_phone_number) {
                        findNavController().navigate(
                            PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentCertificationNumber(
                                OnboardingType.SIGNUP
                            )
                        )
                    }
                } else {
                    navigate(R.id.fragment_phone_number) {
                        findNavController().navigate(
                            PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentLoginPassword(
                                OnboardingType.LOGIN
                            )
                        )
                    }
                }
            } else {
                binding.tvWarning.visibility = View.VISIBLE
                binding.tvPhoneNumberGuide.visibility = View.GONE
                binding.phoneNumberBackground.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
                binding.phoneNumberGuideBackground.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
            }
        }
    }

    private fun checkPhoneRegex(phoneNumber: String): Boolean {
        return Pattern.matches("^01(?:0|1|[6-9])[0-9]{8}", phoneNumber)
    }

    private fun initView() {
        inputMethodManager.showSoftInput(binding.etPhoneNumber, 0)

        binding.etPhoneNumber.addTextChangedListener {
            binding.btnNext.isEnabled = "$it".length == 11
        }
    }
}
