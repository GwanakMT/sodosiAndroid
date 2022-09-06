package com.sodosi.ui.onboarding.nickname

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentNicknameBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  NicknameFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class NicknameFragment : BaseFragment<OnboardingViewModel, FragmentNicknameBinding>() {
    override fun getViewBinding() = FragmentNicknameBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        initView()

        setOnClickListener()
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.isNicknamePossible.collect { isPossible ->
                if (isPossible) {
                    // 2-1) 닉네임이 사용 가능하다면 회원가입 로직 실행
                    val nickname = binding.etNickname.text.toString()
                    val phoneNumber = arguments?.getString("phone_number") ?: return@collect
                    val password = arguments?.getString("password") ?: return@collect

                    viewModel.signUp(phoneNumber, nickname, password)
                } else {
                    // 2-2) 이미 있는 닉네임이라면 Error View
                    binding.tvWarning.visibility = View.VISIBLE
                    binding.inputBackground.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
                }
            }
        }

        repeatOnStarted {
            viewModel.isSignUpSuccess.collect { isSigned ->
                if (isSigned) {
                    // 3-1) 회원가입 성공했으면 웰컴 화면으로 이동
                    val nickname = binding.etNickname.text.toString()
                    moveToWelcomeScreen(nickname)
                } else {
                    // 3-2) 회원가입 실패하면
                    // TODO
                }
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

    private fun initView() {
        binding.etNickname.addTextChangedListener {
            binding.btnFinish.isEnabled = "$it".length != 0
        }
    }

    private fun setOnClickListener() {
        binding.btnFinish.setOnClickListener {
            // 1) 닉네임 중복 체크
            viewModel.checkNickname(binding.etNickname.text.toString())
        }
    }

    private fun moveToWelcomeScreen(nickName: String) {
        navigate(R.id.fragment_nickname) {
            findNavController().navigate(
                NicknameFragmentDirections.actionFragmentNicknameToFragmentWelcome(
                    nickName
                )
            )
        }
    }
}
