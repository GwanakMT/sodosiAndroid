package com.sodosi.ui.onboarding

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentCertificationNumberBinding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  CertificationNumberFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CertificationNumberFragment :
    BaseFragment<OnboardingViewModel, FragmentCertificationNumberBinding>(),
    FirebaseAuthManager.VerificationPhoneListener {
    private lateinit var authManager: FirebaseAuthManager
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentCertificationNumberBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        authManager = FirebaseAuthManager(activity as Activity)

        initAppbar()
        initView()

        setOnClickListener()

        viewModel.startTimer()
    }

    override fun observeData() {
        viewModel.timer.asLiveData().observe(viewLifecycleOwner) {
            binding.tvTimer.text =
                "0${it / 60}:${if (it % 60 < 10) "0${it % 60}" else "${it % 60}"}"
        }
    }

    override fun onAuthSuccess() {
        Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
        findNavController().navigate(Step3FragmentDirections.actionFragmentStep3ToFragmentStep4())
    }

    override fun onAuthFail() {
        Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show()
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            if (viewModel.timer.value > 0) {
                val smsCode =
                    "${binding.etCode1.text}${binding.etCode2.text}${binding.etCode3.text}${binding.etCode4.text}${binding.etCode5.text}${binding.etCode6.text}"
                authManager.signInWithPhoneAuthCredential(smsCode, this)
            } else {
                Toast.makeText(
                    context,
                    "인증번호가 발송된지 3분이 지났습니다. 인증번호 재전송 버튼을 눌러주세요",
                    Toast.LENGTH_SHORT
                ).show()
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
        inputMethodManager.showSoftInput(binding.etCode1, 0)
        binding.btnNext.setStateDisable()

        binding.etCode1.set()
        binding.etCode2.set()
        binding.etCode3.set()
        binding.etCode4.set()
        binding.etCode5.set()
        binding.etCode6.set()
    }

    private fun EditText.set() {
        setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (
                    keyCode == KeyEvent.KEYCODE_0
                    || keyCode == KeyEvent.KEYCODE_1
                    || keyCode == KeyEvent.KEYCODE_2
                    || keyCode == KeyEvent.KEYCODE_3
                    || keyCode == KeyEvent.KEYCODE_4
                    || keyCode == KeyEvent.KEYCODE_5
                    || keyCode == KeyEvent.KEYCODE_6
                    || keyCode == KeyEvent.KEYCODE_7
                    || keyCode == KeyEvent.KEYCODE_8
                    || keyCode == KeyEvent.KEYCODE_9
                ) {
                    when (id) {
                        R.id.etCode1 -> binding.etCode2.requestFocus()
                        R.id.etCode2 -> binding.etCode3.requestFocus()
                        R.id.etCode3 -> binding.etCode4.requestFocus()
                        R.id.etCode4 -> binding.etCode5.requestFocus()
                        R.id.etCode5 -> binding.etCode6.requestFocus()
                    }
                } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                    when (id) {
                        R.id.etCode2 -> binding.etCode1.requestFocus()
                        R.id.etCode3 -> binding.etCode2.requestFocus()
                        R.id.etCode4 -> binding.etCode3.requestFocus()
                        R.id.etCode5 -> binding.etCode4.requestFocus()
                        R.id.etCode6 -> binding.etCode5.requestFocus()
                    }
                }
            }

            false
        }

        addTextChangedListener {
            val code =
                "${binding.etCode1.text}${binding.etCode2.text}${binding.etCode3.text}${binding.etCode4.text}${binding.etCode5.text}${binding.etCode6.text}"
            if (code.length == 6) {
                binding.btnNext.setStateNormal()
            } else {
                binding.btnNext.setStateDisable()
            }
        }
    }
}
