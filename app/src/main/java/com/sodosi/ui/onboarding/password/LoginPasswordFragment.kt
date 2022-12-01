package com.sodosi.ui.onboarding.password

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentLoginPasswordBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.main.MainActivity
import com.sodosi.ui.onboarding.OnboardingViewModel
import com.sodosi.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 *  SignPasswordFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class LoginPasswordFragment : BaseFragment<OnboardingViewModel, FragmentLoginPasswordBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentLoginPasswordBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()

        initView()
    }

    override fun observeData() {
        viewModel.isLoginSuccess.asLiveData().observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == true) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)

                activity?.finish()
            } else if (isSuccess == false) {
                binding.etPassword.setViewWarning()
            }
        }
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
            val password: String = binding.etPassword.getText()
            val phoneNumber = arguments?.getString("phone_number") ?: ""

            viewModel.login(phoneNumber, password)
        }

        binding.tvForgotPassword.setOnClickListener {
            val phoneNumber = arguments?.getString("phone_number") ?: ""

            navigate(R.id.fragment_login_password) {
                findNavController().navigate(LoginPasswordFragmentDirections.actionFragmentLoginPasswordToFragmentCertificationNumber(
                    phoneNumber = phoneNumber
                ))
            }
        }
    }

    private fun initView() {
        binding.etPassword.setHint(getString(R.string.onboarding_password_hint))
        binding.etPassword.textChangedListener = {
            binding.btnNext.isEnabled = "$it".length >= 8
        }

        inputMethodManager.showSoftInput(binding.etPassword, 0)
    }
}
