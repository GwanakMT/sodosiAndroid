package com.sodosi.ui.setting

import android.content.pm.PackageInfo
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySettingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>(),
    View.OnClickListener {
    override val viewModel: SettingViewModel by viewModels()

    override fun getViewBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.phoneNumber.collect {
                initView(it)
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
            binding.termsOfService -> moveToWebDocs()
            binding.privacyPolicy -> moveToWebDocs()
            binding.openSourceInfo -> moveToWebDocs()
            binding.versionName -> moveToVersionInfo()
            binding.sodosiMakers -> moveToSodosiMakers()
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

    private fun initView(phoneNumber: String) {
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
        SodosiToast.makeText(this, "비밀번호 변경", Toast.LENGTH_SHORT).show()
    }

    private fun moveToSettingNotification() {
        SodosiToast.makeText(this, "앱 알림 설정", Toast.LENGTH_SHORT).show()
    }

    private fun moveToWebDocs() {
        SodosiToast.makeText(this, "약관 및 정책", Toast.LENGTH_SHORT).show()
    }

    private fun moveToVersionInfo() {
        SodosiToast.makeText(this, "버전 정보", Toast.LENGTH_SHORT).show()
    }

    private fun moveToSodosiMakers() {
        SodosiToast.makeText(this, "소도시를 만든 사람들", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        SodosiToast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        SodosiToast.makeText(this, "탈퇴하기", Toast.LENGTH_SHORT).show()
    }
}
