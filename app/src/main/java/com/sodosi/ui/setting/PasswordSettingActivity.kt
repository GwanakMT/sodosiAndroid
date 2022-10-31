package com.sodosi.ui.setting

import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityPasswordSettingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordSettingActivity : BaseActivity<SettingViewModel, ActivityPasswordSettingBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityPasswordSettingBinding.inflate(layoutInflater)

    private var textListener1: Boolean = false
    private var textListener2: Boolean = false
    private var textListener3: Boolean = false

    override fun observeData() {
        repeatOnStarted {
            viewModel.changePasswordEvent.collect {
                progress.dismiss()
                when(it) {
                    1 -> setSuccessView()
                    2 -> setErrorViewCurrentPasswordNotMatched()
                    3 -> setErrorViewFailNetwork()
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        initAppbar()
        initView()
        setOnClickListener()

        binding.etBeforePassword.setFocus()
        inputMethodManager.showSoftInput(binding.etBeforePassword, 0)

        return@with
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
            checkButtonEnable()
        }

        binding.etAfterPassword.textChangedListener = {
            textListener2 = "$it".length >= 8
            checkButtonEnable()
        }

        binding.etAfterPasswordCheck.textChangedListener = {
            textListener3 = "$it".length >= 8
            checkButtonEnable()
        }
    }

    private fun checkButtonEnable() {
        if (textListener1 && textListener2 && textListener3) {
            binding.btnFinish.setTextColor(Color.parseColor("#0F0F10"))
        } else {
            binding.btnFinish.setTextColor(Color.parseColor("#ADADAD"))
        }
    }

    private fun setOnClickListener() {
        binding.btnFinish.setOnClickListener {
            if (textListener1 && textListener2 && textListener3) {
                if (binding.etAfterPassword.getText().length < 8) {
                    setErrorViewPasswordIsNotSafe()
                } else {
                    if (binding.etAfterPassword.getText() == binding.etAfterPasswordCheck.getText()) {
                        // 변경할 비밀번호와 변경할 비밀번호 확인이 일치할 경우
                        viewModel.changePassword(binding.etBeforePassword.getText(), binding.etAfterPassword.getText())
                    } else {
                        // 일치하지 않는다면
                        setErrorViewPasswordNotMatched()
                    }
                }
            }
        }
    }

    // 비밀번호가 일치하지 않아요. (현재 비밀번호)
    private fun setErrorViewCurrentPasswordNotMatched() {
        with(binding) {
            etBeforePassword.setViewWarning("비밀번호가 일치하지 않아요.")
            etAfterPassword.disableWarning()
            etAfterPasswordCheck.disableWarning()
        }
    }

    // 비밀번호가 일치하지 않아요.
    private fun setErrorViewPasswordNotMatched() {
        with(binding) {
            etBeforePassword.disableWarning()
            etAfterPassword.disableWarning()
            etAfterPasswordCheck.setViewWarning("비밀번호가 일치하지 않아요.")
        }
    }

    // 비밀번호는 8자 이상이어야 해요.
    private fun setErrorViewPasswordIsNotSafe() {
        with(binding) {
            etBeforePassword.disableWarning()
            etAfterPassword.setViewWarning("비밀번호는 8자 이상이어야 해요.")
            etAfterPasswordCheck.disableWarning()
        }
    }

    private fun setSuccessView() {
        SodosiToast.makeText(this, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show()
        finish()
    }

    // 비밀번호 변경에 실패했습니다.
    private fun setErrorViewFailNetwork() {
        SodosiToast.makeText(this, "비밀번호 변경 실패...", Toast.LENGTH_SHORT).show()
    }
}
