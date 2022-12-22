package com.sodosi.ui.setting

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityPhoneNumberCertificationBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.onboarding.certification.FirebaseAuthManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberCertificationActivity :
    BaseActivity<SettingViewModel, ActivityPhoneNumberCertificationBinding>(),
    FirebaseAuthManager.VerificationPhoneListener {
    override val viewModel: SettingViewModel by viewModels()

    private var newPhoneNumber = ""
    private var resendCount = 0

    private lateinit var authManager: FirebaseAuthManager
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun getViewBinding() = ActivityPhoneNumberCertificationBinding.inflate(layoutInflater)

    override fun onAuthSuccess() {
        viewModel.changePhoneNumber(newPhoneNumber)
    }

    override fun onAuthFail() {
        setCertificationWarning(resources.getString(R.string.onboarding_sms_code_warning))
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.timer.collect {
                binding.tvTimer.text =
                    "0${it / 60}:${if (it % 60 < 10) "0${it % 60}" else "${it % 60}"}"
            }
        }

        repeatOnStarted {
            viewModel.changePhoneNumberEvent.collect { changeSuccess ->
                if (changeSuccess) {
                    // 핸드폰 번호 변경 성공 처리
                    SodosiToast.makeText(
                        this@PhoneNumberCertificationActivity,
                        "휴대폰 번호가 변경됐어요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@PhoneNumberCertificationActivity, SettingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivity(intent)
                } else {
                    // 핸드폰 번호 변경 실패 처리
                    SodosiToast.makeText(
                        this@PhoneNumberCertificationActivity,
                        "핸드폰 번호 변경이 실패했습니다. 다시 시도해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun initViews() {
        newPhoneNumber = intent.getStringExtra(KEY_PHONE_NUMBER) ?: return

        authManager = FirebaseAuthManager(this)
        authManager.verifyPhoneNumber("+82${newPhoneNumber.toInt()}")

        initAppbar()
        initView()

        viewModel.resetTimer()

        binding.btnSubmit.setOnClickListener {
            if (binding.etCertificationNumber.text.length < 6) {
                SodosiToast.makeText(this, "인증번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (viewModel.timer.value > 0) {
                    val smsCode = binding.etCertificationNumber.text.toString()
                    authManager.signInWithPhoneAuthCredential(smsCode, this)
                } else {
                    setCertificationWarning(resources.getString(R.string.onboarding_timer_warning))
                }
            }
        }

        binding.tvResend.setOnClickListener {
            if (resendCount < 3) {
                resendCount += 1
                authManager.verifyPhoneNumber("+82${newPhoneNumber.toInt()}")
                viewModel.resetTimer()
                SodosiToast.makeText(this, "인증번호가 재전송 되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initAppbarTitle(getString(R.string.setting_phone_number_title))
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }
    }

    private fun initView() {
        binding.tvCode1.set()
        binding.tvCode2.set()
        binding.tvCode3.set()
        binding.tvCode4.set()
        binding.tvCode5.set()
        binding.tvCode6.set()

        binding.etCertificationNumber.addTextChangedListener {
            with(it.toString()) {
                binding.tvCode1.text = getNumberWithIndex(0)
                binding.tvCode2.text = getNumberWithIndex(1)
                binding.tvCode3.text = getNumberWithIndex(2)
                binding.tvCode4.text = getNumberWithIndex(3)
                binding.tvCode5.text = getNumberWithIndex(4)
                binding.tvCode6.text = getNumberWithIndex(5)
            }
        }

        inputMethodManager.showSoftInput(binding.etCertificationNumber, 0)
    }

    private fun String.getNumberWithIndex(index: Int): String {
        return getOrNull(index)?.toString() ?: ""
    }

    private fun TextView.set() {
        setOnClickListener {
            binding.etCertificationNumber.requestFocus()
            inputMethodManager.showSoftInput(binding.etCertificationNumber, 0)
        }
    }

    private fun setCertificationWarning(message: String) {
        binding.tvWarning.visibility = View.VISIBLE

        binding.tvWarning.text = message
        binding.tvCode1.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
        binding.tvCode2.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
        binding.tvCode3.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
        binding.tvCode4.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
        binding.tvCode5.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
        binding.tvCode6.background =
            ContextCompat.getDrawable(this, R.drawable.background_rounded_pink)
    }

    companion object {
        private const val KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER"

        fun getIntent(context: Context, newPhoneNumber: String): Intent {
            return Intent(context, PhoneNumberCertificationActivity::class.java).apply {
                putExtra(KEY_PHONE_NUMBER, newPhoneNumber)
            }
        }
    }
}