package com.sodosi.ui.onboarding

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentLoginPasswordBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.main.MainActivity

/**
 *  SignPasswordFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class LoginPasswordFragment : BaseFragment<OnboardingViewModel, FragmentLoginPasswordBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentLoginPasswordBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        inputMethodManager.showSoftInput(binding.etPassword, 0)

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
            viewModel.login()
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LoginPasswordFragmentDirections.actionFragmentLoginPasswordToFragmentCertificationNumber())
        }
    }

    private fun initView() {
        binding.btnNext.setStateDisable()
        binding.etPassword.textChangedListener = {
            if ("$it".length >= 8) {
                binding.btnNext.setStateNormal()
            } else {
                binding.btnNext.setStateDisable()
            }
        }
    }
}
