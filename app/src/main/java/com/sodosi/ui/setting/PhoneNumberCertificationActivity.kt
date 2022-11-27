package com.sodosi.ui.setting

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.sodosi.databinding.ActivityPhoneNumberCertificationBinding
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberCertificationActivity : BaseActivity<SettingViewModel, ActivityPhoneNumberCertificationBinding>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityPhoneNumberCertificationBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {

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