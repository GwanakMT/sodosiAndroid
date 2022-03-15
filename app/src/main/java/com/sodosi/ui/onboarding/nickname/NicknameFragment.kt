package com.sodosi.ui.onboarding.nickname

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.DialogOnboardingTermsBinding
import com.sodosi.databinding.FragmentNicknameBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.onboarding.OnboardingViewModel

/**
 *  NicknameFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class NicknameFragment : BaseFragment<OnboardingViewModel, FragmentNicknameBinding>() {
    private lateinit var termsDialog: Dialog

    override fun getViewBinding() = FragmentNicknameBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        initView()

        setOnClickListener()

        showTermsDialog()
    }

    override fun observeData() {
        viewModel.isNicknamePossible.asLiveData().observe(viewLifecycleOwner) { isPossible ->
            if (isPossible == true) {
                val nickname = binding.etNickname.text.toString()
                findNavController().navigate(
                    NicknameFragmentDirections.actionFragmentNicknameToFragmentWelcome(
                        nickname
                    )
                )
            } else {
                binding.tvWarning.visibility = View.VISIBLE
                binding.inputBackground.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_rounded_pink)
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
        binding.btnFinish.setStateDisable()
        binding.etNickname.addTextChangedListener {
            if ("$it".length == 0) {
                binding.btnFinish.setStateDisable()
            } else {
                binding.btnFinish.setStateNormal()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnFinish.setOnClickListener {
            viewModel.checkNickname(binding.etNickname.text.toString())
        }
    }

    private fun showTermsDialog() {
        termsDialog = Dialog(requireContext())
        termsDialog.apply {
            val binding = DialogOnboardingTermsBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnAllow.setStateDisable()
            binding.btnAllow.setOnClickListener {
                termsDialog.dismiss()
            }

            binding.rvTerms.apply {
                itemAnimator?.changeDuration = 0
                adapter = TermsAdapter().apply {
                    submitList(viewModel.getTerms())
                    onItemClick = {
                        val intent = Intent(context, TermsDetailActivity::class.java)
                        startActivity(intent)
                    }

                    isAllowAll.asLiveData().observe(viewLifecycleOwner) {
                        if (it) {
                            binding.btnAllow.setStateNormal()
                            binding.tvAllowAllTerms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_checked_28, 0, 0, 0)
                        } else {
                            binding.btnAllow.setStateDisable()
                            binding.tvAllowAllTerms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_unchecked_28, 0, 0, 0)
                        }
                    }
                }
            }

            binding.tvAllowAllTerms.setOnClickListener {
                val isAllowAll = (binding.rvTerms.adapter as TermsAdapter).isAllowAll.value
                (binding.rvTerms.adapter as TermsAdapter).submitList((binding.rvTerms.adapter as TermsAdapter).currentList.map {
                    it.copy(isAgree = !isAllowAll)
                })
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }

            setCancelable(false)
            setCanceledOnTouchOutside(false)

            show()
        }
    }
}
