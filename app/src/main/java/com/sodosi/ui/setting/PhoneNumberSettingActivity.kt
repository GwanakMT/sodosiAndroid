package com.sodosi.ui.setting

import android.content.Context
import android.content.Intent
import android.telephony.PhoneNumberUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityPhoneNumberSettingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.regex.Pattern

@AndroidEntryPoint
class PhoneNumberSettingActivity : BaseActivity<SettingViewModel, ActivityPhoneNumberSettingBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityPhoneNumberSettingBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        ininAppBar()

        val phoneNumberNow = intent.getStringExtra(KEY_PHONE_NUMBER)
        binding.tvPhoneNumberNowPlaceHolder.text = PhoneNumberUtils.formatNumber(phoneNumberNow, Locale.getDefault().country)
        inputMethodManager.showSoftInput(binding.tvPhoneNumberNew, 0)

        binding.btnNext.setOnClickListener {
            val phoneNumberNew = binding.etPhoneNumberNew.text.toString()
            if (checkPhoneRegex(phoneNumberNew)) {
                if (phoneNumberNow == phoneNumberNew) {
                    SodosiToast.makeText(this, "굿", Toast.LENGTH_SHORT).show()
                } else {
                    SodosiToast.makeText(this, "기존 번호와 똑같아요", Toast.LENGTH_SHORT).show()
                }
            } else {
                SodosiToast.makeText(this, "핸드폰 번호를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ininAppBar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.setting_phone_number_title))
        }
    }

    private fun checkPhoneRegex(phoneNumber: String): Boolean {
        return Pattern.matches("^01(?:0|1|[6-9])[0-9]{8}", phoneNumber)
    }

    companion object {
        private const val KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER"
        fun getIntent(context: Context, nowPhoneNumber: String): Intent {
            return Intent(context, PhoneNumberSettingActivity::class.java).apply {
                putExtra(KEY_PHONE_NUMBER, nowPhoneNumber)
            }
        }
    }
}