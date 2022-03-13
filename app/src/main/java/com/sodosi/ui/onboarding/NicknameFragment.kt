package com.sodosi.ui.onboarding

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.FragmentNicknameBinding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  NicknameFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class NicknameFragment : BaseFragment<OnboardingViewModel, FragmentNicknameBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = FragmentNicknameBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        initView()

        setOnClickListener()
    }

    override fun observeData() {
        viewModel.isNicknamePossible.asLiveData().observe(viewLifecycleOwner) { isPossible ->
            if (isPossible == true) {
                findNavController().navigate(NicknameFragmentDirections.actionFragmentNicknameToFragmentWelcome())
            } else if (isPossible == false){
                binding.tvWarning.visibility = View.VISIBLE
                binding.inputBackground.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
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
        inputMethodManager.showSoftInput(binding.etNickname, 0)

        binding.btnFinish.setStateDisable()
        binding.etNickname.addTextChangedListener {
            if ("$it".length == 0) {
                binding.btnFinish.setStateDisable()
                binding.etNickname.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.pretendard_regular)
            } else {
                binding.btnFinish.setStateNormal()
                binding.etNickname.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.pretendard_semibold)
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnFinish.setOnClickListener {
            viewModel.checkNickname(binding.etNickname.text.toString())
        }
    }
}
