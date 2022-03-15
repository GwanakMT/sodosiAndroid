package com.sodosi.ui.onboarding.password

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentSignPasswordBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

/**
 *  SignPasswordFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SignPasswordFragment : BaseFragment<OnboardingViewModel, FragmentSignPasswordBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private val passwordChecker = MutableStateFlow(false)
    private val rePasswordChecker = MutableStateFlow(false)
    private val buttonEnable = passwordChecker.combine(rePasswordChecker) { checker1, checker2 ->
        checker1 && checker2
    }

    override fun getViewBinding() = FragmentSignPasswordBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        initView()
        setOnClickListener()
    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            buttonEnable.collect {
                if (it) {
                    binding.btnNext.setStateNormal()
                } else {
                    binding.btnNext.setStateDisable()
                }
            }
        }

        viewModel.isSignSuccess.asLiveData().observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == true) {
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                findNavController().navigate(SignPasswordFragmentDirections.actionFragmentSignPasswordToFragmentNickname())
            } else if (isSuccess == false) {
                // 회원가입 실패 시
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
            val password = binding.etPassword.getText()
            val rePassword = binding.etRePassword.getText()
            if (password == rePassword) {
                viewModel.singIn()
            } else {
                binding.etRePassword.setViewWarning()
            }
        }
    }

    private fun initView() {
        binding.btnNext.setStateDisable()

        binding.etPassword.apply {
            setHint(getString(R.string.onboarding_password_hint))
            textChangedListener = {
                passwordChecker.value = "$it".length >= 8
            }
        }

        binding.etRePassword.apply {
            setHint(getString(R.string.onboarding_repassword_hint))
            textChangedListener = {
                rePasswordChecker.value = "$it".length >= 8
            }
        }
    }
}
