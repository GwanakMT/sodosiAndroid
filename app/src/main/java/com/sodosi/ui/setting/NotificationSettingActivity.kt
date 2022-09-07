package com.sodosi.ui.setting

import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityNotificationSettingBinding
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationSettingActivity : BaseActivity<SettingViewModel, ActivityNotificationSettingBinding>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivityNotificationSettingBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.setting_notification))
        }
    }
}
