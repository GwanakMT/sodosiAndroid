package com.sodosi.ui.setting

import android.content.Intent
import android.content.pm.PackageInfo
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySettingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>(),
    View.OnClickListener {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.phoneNumber.collect {
                setData(it)
            }
        }

        repeatOnStarted {
            viewModel.deleteUserEvent.collect { isSuccess ->
                progress.dismiss()
                if (isSuccess) {
                    SodosiToast.makeText(this@SettingActivity, "탈퇴 성공", Toast.LENGTH_SHORT).show()
                    clearAndMoveToOnboarding()
                } else {
                    SodosiToast.makeText(this@SettingActivity, "탈퇴 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.settingPhoneNumber.setOnClickListener(this)
        binding.settingPassword.setOnClickListener(this)
        binding.settingNotification.setOnClickListener(this)
        binding.termsOfService.setOnClickListener(this)
        binding.privacyPolicy.setOnClickListener(this)
        binding.openSourceInfo.setOnClickListener(this)
        binding.versionName.setOnClickListener(this)
        binding.sodosiMakers.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
        binding.deleteUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.settingPhoneNumber -> moveToSettingPhoneNumber()
            binding.settingPassword -> moveToSettingPassword()
            binding.settingNotification -> moveToSettingNotification()
            binding.termsOfService -> moveToWebDocs(WebViewActivity.TYPE_TERMS_OF_SERVICE)
            binding.privacyPolicy -> moveToWebDocs(WebViewActivity.TYPE_PRIVACY_POLICY)
            binding.openSourceInfo -> moveToWebDocs(WebViewActivity.TYPE_OPEN_SOURCE_INFO)
            binding.versionName -> moveToVersionInfo()
            binding.sodosiMakers -> moveToWebDocs(WebViewActivity.TYPE_SODOSI_MAKERS)
            binding.logout -> logout()
            binding.deleteUser -> deleteUser()
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initAppbarTitle(getString(R.string.setting_title))

            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }
    }

    private fun setData(phoneNumber: String) {
        binding.versionName.text = getVersionName()
        binding.phoneNumber.text = phoneNumber
    }

    private fun getVersionName(): String {
        val info: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        return info.versionName
    }

    private fun moveToSettingPhoneNumber() {
        SodosiToast.makeText(this, "전화번호", Toast.LENGTH_SHORT).show()
    }

    private fun moveToSettingPassword() {
        val intent = Intent(this, PasswordSettingActivity::class.java)
        startActivity(intent)
    }

    private fun moveToSettingNotification() {
        val intent = Intent(this, NotificationSettingActivity::class.java)
        startActivity(intent)
    }

    private fun moveToWebDocs(type: String) {
        startActivity(WebViewActivity.getIntent(this, type))
    }

    private fun moveToVersionInfo() {
        SodosiToast.makeText(this, "버전 정보", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        viewModel.logout()

        val intent = Intent(this, OnboardingActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

    private fun deleteUser() {
        progress.show()
        viewModel.deleteUser()
    }
}

