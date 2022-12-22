package com.sodosi.ui.setting

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.databinding.ActivityPhoneNumberCertificationBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberCertificationActivity : BaseActivity<SettingViewModel, ActivityPhoneNumberCertificationBinding>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityPhoneNumberCertificationBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.changePhoneNumberEvent.collect { changeSuccess ->
                if (changeSuccess) {
                    // 핸드폰 번호 변경 성공 처리
                    SodosiToast.makeText(
                        this@PhoneNumberCertificationActivity,
                        "핸드폰 번호 변경 성공",
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
        binding.btnSubmit.setOnClickListener {
            val newPhoneNumber = intent.getStringExtra(KEY_PHONE_NUMBER) ?: return@setOnClickListener
            viewModel.changePhoneNumber(newPhoneNumber)
        }
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