package com.sodosi.ui.onboarding.password

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sodosi.R
import com.sodosi.databinding.DialogOnboardingTermsBinding
import com.sodosi.databinding.FragmentSignPasswordBinding
import com.sodosi.domain.entity.Terms
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.navigate
import com.sodosi.ui.onboarding.OnboardingViewModel
import com.sodosi.ui.onboarding.nickname.TermsAdapter
import com.sodosi.ui.onboarding.nickname.TermsDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

/**
 *  SignPasswordFragment.kt
 *
 *  Created by Minji Jeong on 2022/03/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class SignPasswordFragment : BaseFragment<OnboardingViewModel, FragmentSignPasswordBinding>() {
    private lateinit var termsDialog: Dialog
    private var termsDialogOpened = false
    private val inputMethodManager: InputMethodManager by lazy { context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private val passwordChecker = MutableStateFlow(false)
    private val rePasswordChecker = MutableStateFlow(false)
    private val buttonEnable = passwordChecker.combine(rePasswordChecker) { checker1, checker2 ->
        checker1 && checker2
    }

    override fun getViewBinding() = FragmentSignPasswordBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by activityViewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        initView()
        setOnClickListener()
    }

    override fun observeData() {
        repeatOnStarted {
            buttonEnable.collect {
                binding.btnNext.isEnabled = it
            }
        }

        repeatOnStarted {
            viewModel.userPrivacyPolicyTerms.collect {
                val password = binding.etPassword.getText()
                if (it.isNotEmpty()) {
                    showTermsDialog(password, it)
                } else {
                    SodosiToast.makeText(requireContext(), "이용약관을 불러오는데 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                if (!termsDialogOpened) {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            val password = binding.etPassword.getText()
            val rePassword = binding.etRePassword.getText()
            if (password == rePassword) {
                viewModel.getTerms()
            } else {
                binding.etRePassword.setViewWarning()
            }
        }
    }

    private fun initView() {
        binding.etPassword.apply {
            setHint(getString(R.string.onboarding_password_hint))
            setFocus()
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

        inputMethodManager.showSoftInput(binding.etPassword, 0)
    }

    private fun showTermsDialog(password: String, termsList: List<Terms>) {
        termsDialog = Dialog(requireContext())
        termsDialog.apply {
            val binding = DialogOnboardingTermsBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnAllow.setOnClickListener {
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                termsDialog.dismiss()

                viewModel.setAllowTerms((binding.rvTerms.adapter as TermsAdapter).getItems())

                val phoneNumber = arguments?.getString("phone_number") ?: return@setOnClickListener
                navigate(R.id.fragment_sign_password) {
                    findNavController().navigate(
                        SignPasswordFragmentDirections.actionFragmentSignPasswordToFragmentNickname(
                            phoneNumber = phoneNumber,
                            password = password,
                        )
                    )
                }
            }

            binding.rvTerms.apply {
                itemAnimator?.changeDuration = 0
                adapter = TermsAdapter().apply {
                    submitList(termsList)
                    onItemClick = {
                        val intent = Intent(context, TermsDetailActivity::class.java)
                        startActivity(intent)
                    }

                    isAllowAll.asLiveData().observe(viewLifecycleOwner) {
                        if (it) {
                            binding.tvAllowAllTerms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_checked_28, 0, 0, 0)
                        } else {
                            binding.tvAllowAllTerms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_interface_unchecked_28, 0, 0, 0)
                        }
                    }

                    isAllowAllEssentialTerms.asLiveData().observe(viewLifecycleOwner) {
                        binding.btnAllow.isEnabled = it
                    }
                }
            }

            binding.tvAllowAllTerms.setOnClickListener {
                val isAllowAll = (binding.rvTerms.adapter as TermsAdapter).isAllowAll.value
                (binding.rvTerms.adapter as TermsAdapter).submitList((binding.rvTerms.adapter as TermsAdapter).getItems().map {
                    it.copy(isAllow = !isAllowAll)
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
            termsDialogOpened = true
        }
    }
}
