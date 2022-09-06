package com.sodosi.ui.onboarding.certification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentCertificationNumberBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.main.MainActivity
import com.sodosi.ui.onboarding.OnboardingType
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  CertificationNumberFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class CertificationNumberFragment :
    BaseFragment<OnboardingViewModel, FragmentCertificationNumberBinding>(),
    FirebaseAuthManager.VerificationPhoneListener {
    private lateinit var authManager: FirebaseAuthManager
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentCertificationNumberBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        authManager = FirebaseAuthManager(activity as Activity)

        authManager.verifyPhoneNumber()

        initAppbar()
        initView()

        setOnClickListener()

        viewModel.resetTimer()
    }

    override fun observeData() {
        viewModel.timer.asLiveData().observe(viewLifecycleOwner) {
            binding.tvTimer.text =
                "0${it / 60}:${if (it % 60 < 10) "0${it % 60}" else "${it % 60}"}"
        }
    }

    override fun onAuthSuccess() {
        val onboardingType = arguments?.get("onboarding_type")
        val phoneNumber = arguments?.getString("phone_number") ?: return

        if (onboardingType == OnboardingType.SIGNUP) {
            navigate(R.id.fragment_certification_number) {
                findNavController().navigate(
                    CertificationNumberFragmentDirections.actionFragmentCertificationNumberToFragmentSignPassword(
                        phoneNumber = phoneNumber,
                        onboardingType = OnboardingType.SIGNUP
                    )
                )
            }
        } else {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }
    }

    override fun onAuthFail() {
        setCertificationWarning(resources.getString(R.string.onboarding_sms_code_warning))
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            if (viewModel.timer.value > 0) {
                val smsCode = binding.etCertificationNumber.text.toString()
                authManager.signInWithPhoneAuthCredential(smsCode, this)
            } else {
                setCertificationWarning(resources.getString(R.string.onboarding_timer_warning))
            }
        }

        binding.tvResend.setOnClickListener {
            authManager.verifyPhoneNumber()
            viewModel.resetTimer()
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                activity?.onBackPressed()
            }
        }
    }

    private fun initView() {
        binding.tvCode1.set()
        binding.tvCode2.set()
        binding.tvCode3.set()
        binding.tvCode4.set()
        binding.tvCode5.set()
        binding.tvCode6.set()

        binding.etCertificationNumber.addTextChangedListener {
            with(it.toString()) {
                binding.tvCode1.text = getNumberWithIndex(0)
                binding.tvCode2.text = getNumberWithIndex(1)
                binding.tvCode3.text = getNumberWithIndex(2)
                binding.tvCode4.text = getNumberWithIndex(3)
                binding.tvCode5.text = getNumberWithIndex(4)
                binding.tvCode6.text = getNumberWithIndex(5)
            }
            if (it?.length ?: 0 >= 6) binding.btnNext.isEnabled = true
        }
    }

    private fun String.getNumberWithIndex(index: Int): String {
        return getOrNull(index)?.toString() ?: ""
    }

    private fun TextView.set() {
        setOnClickListener {
            binding.etCertificationNumber.requestFocus()
            inputMethodManager.showSoftInput(binding.etCertificationNumber, 0)
        }
    }

    private fun setCertificationWarning(message: String) {
        binding.tvWarning.visibility = View.VISIBLE

        binding.tvWarning.text = message
        binding.tvCode1.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
        binding.tvCode2.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
        binding.tvCode3.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
        binding.tvCode4.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
        binding.tvCode5.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
        binding.tvCode6.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
    }
}
