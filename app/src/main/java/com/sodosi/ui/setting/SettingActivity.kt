package com.sodosi.ui.setting

import android.content.Intent
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySettingBinding
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()

    }

    private fun initAppbar() {
        binding.appbar.apply {
            initAppbarTitle(getString(R.string.setting_title))

            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
    }
}
