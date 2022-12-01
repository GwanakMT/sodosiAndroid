package com.sodosi.ui.onboarding.phonenumber

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentPhoneNumberBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.onboarding.OnboardingType
import com.sodosi.ui.onboarding.OnboardingViewModel
import com.sodosi.ui.onboarding.certification.FirebaseAuthManager
import com.sodosi.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

/**
 *  PhoneNumberFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/12
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class PhoneNumberFragment : BaseFragment<OnboardingViewModel, FragmentPhoneNumberBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentPhoneNumberBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {
        repeatOnStarted {
            // 2-2) 핸드폰 번호 중복 체크. (이미 회원가입 되어있는지 확인)
            viewModel.userNotJoined.collect { userNotJoined ->
                progress.dismiss()

                val phoneNumber = binding.etPhoneNumber.text.toString()
                if (arguments?.get("onboarding_type") == OnboardingType.SIGNUP) {
                    // 회원가입 FLAG
                    if (userNotJoined) {
                        // 가입되어있지 않다면 다음 단계(휴대폰 인증 페이지)로 이동
                        moveToSignUpScreen(phoneNumber)
                    } else {
                        // 이미 가입되어있다면 토스트 메세지와 함께 비밀번호 페이지로 이동
                        SodosiToast.makeText(requireContext(), "이미 가입되어있는 번호입니다.", Toast.LENGTH_SHORT).show()
                        moveToLoginScreen(phoneNumber)
                    }
                } else {
                    // 로그인 FLAG
                    if (userNotJoined) {
                        // 가입되어있지 않다면 회원가입 단계로 이동
                        SodosiToast.makeText(requireContext(), "가입되지 않은 핸드폰 번호입니다..", Toast.LENGTH_SHORT).show()
                        moveToSignUpScreen(phoneNumber)
                    } else {
                        // 가입되어있는게 맞다면 비밀번호 페이지로 이동
                        moveToLoginScreen(phoneNumber)
                    }
                }
            }
        }
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

            // 1-1) 유효한 핸드폰 번호인지 확인
            if (checkPhoneRegex(phoneNumber)) {
                FirebaseAuthManager.phoneNumber = "+82${phoneNumber.toInt()}"

                viewModel.checkUserNotJoined(phoneNumber)
                progress.show()
            } else {
                // 1-2) 핸드폰 번호가 유효하지 않다면 Error View 처리
                binding.tvWarning.visibility = View.VISIBLE
                binding.tvPhoneNumberGuide.visibility = View.GONE
                binding.phoneNumberBackground.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
                binding.phoneNumberGuideBackground.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
            }
        }
    }

    private fun checkPhoneRegex(phoneNumber: String): Boolean {
        return Pattern.matches("^01(?:0|1|[6-9])[0-9]{8}", phoneNumber)
    }

    private fun moveToSignUpScreen(phoneNumber: String) {
        navigate(R.id.fragment_phone_number) {
            findNavController().navigate(
                PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentCertificationNumber(
                    onboardingType = OnboardingType.SIGNUP,
                    phoneNumber = phoneNumber
                )
            )
        }
    }

    private fun moveToLoginScreen(phoneNumber: String) {
        navigate(R.id.fragment_phone_number) {
            findNavController().navigate(
                PhoneNumberFragmentDirections.actionFragmentPhoneNumberToFragmentLoginPassword(
                    phoneNumber = phoneNumber,
                    onboardingType = OnboardingType.LOGIN
                )
            )
        }
    }

    private fun initView() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_DENIED) {
            PermissionManager.getPermission(requireActivity(), android.Manifest.permission.RECEIVE_SMS)
        }

        inputMethodManager.showSoftInput(binding.etPhoneNumber, 0)

        binding.etPhoneNumber.addTextChangedListener {
            binding.btnNext.isEnabled = "$it".length == 11
        }

        if (arguments?.get("onboarding_type") == OnboardingType.LOGIN) {
            binding.tvPhoneNumberGuide.setGone()
        }
    }
}
