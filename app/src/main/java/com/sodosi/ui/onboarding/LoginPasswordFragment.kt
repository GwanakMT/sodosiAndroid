package com.sodosi.ui.onboarding

import android.content.Intent
import androidx.fragment.app.viewModels
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
    override fun getViewBinding() = FragmentLoginPasswordBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()
    }

    override fun observeData() {

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
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LoginPasswordFragmentDirections.actionFragmentLoginPasswordToFragmentCertificationNumber())
        }
    }
}
