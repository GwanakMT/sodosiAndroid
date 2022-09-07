package com.sodosi.ui.setting

import android.graphics.Color
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityPasswordSettingBinding
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordSettingActivity : BaseActivity<SettingViewModel, ActivityPasswordSettingBinding>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityPasswordSettingBinding.inflate(layoutInflater)

    private var textListener1: Boolean = false
    private var textListener2: Boolean = false
    private var textListener3: Boolean = false

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
        initView()
        setOnClickListener()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.setting_password))
        }
    }

    private fun initView() {
        binding.etBeforePassword.setHint(getString(R.string.setting_password_before))
        binding.etAfterPassword.setHint(getString(R.string.setting_password_after))
        binding.etAfterPasswordCheck.setHint(getString(R.string.setting_password_after_check))

        binding.etBeforePassword.textChangedListener = {
            textListener1 = "$it".length >= 8
            checkSubmitButtonState()
        }

        binding.etAfterPassword.textChangedListener = {
            textListener2 = "$it".length >= 8
            checkSubmitButtonState()
        }

        binding.etAfterPasswordCheck.textChangedListener = {
            textListener3 = "$it".length >= 8
            checkSubmitButtonState()
        }
    }

    private fun checkSubmitButtonState() {
        if (textListener1 && textListener2 && textListener3) {
            binding.btnFinish.setTextColor(Color.parseColor("#0F0F10"))
        } else {
            binding.btnFinish.setTextColor(Color.parseColor("#ADADAD"))
        }
    }

    private fun setOnClickListener() {
        binding.btnFinish.setOnClickListener {
            if (textListener1 && textListener2 && textListener3) {
                // TODO
            }
        }
    }
}
