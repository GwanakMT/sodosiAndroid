package com.sodosi.ui.setting

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
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
    private lateinit var deleteUserDialog: Dialog
    private var phoneNumber = ""

    override fun getViewBinding() = ActivitySettingBinding.inflate(layoutInflater)

    private val changePhoneNumberLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getPhoneNumber()
        }
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.phoneNumber.collect {
                phoneNumber = it
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
            binding.openSourceInfo -> moveToOpenSourceLicensePage()
            binding.versionName -> moveToVersionInfo()
            binding.sodosiMakers -> moveToSodosiMakers()
            binding.logout -> logout()
            binding.deleteUser -> {
                deleteUserDialog = Dialog(this).apply {
                    setContentView(R.layout.dialog_user_exit)

                    findViewById<TextView>(R.id.btnDeleteUser).setOnClickListener {
                        deleteUserDialog.dismiss()
                        deleteUser()
                    }

                    findViewById<TextView>(R.id.btnContinue).setOnClickListener {
                        deleteUserDialog.dismiss()
                    }

                    window?.apply {
                        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setGravity(Gravity.CENTER)
                        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }
                }

                deleteUserDialog.show()
            }
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
        changePhoneNumberLauncher.launch(PhoneNumberSettingActivity.getIntent(this, phoneNumber))
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

    private fun moveToOpenSourceLicensePage() {
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        OssLicensesMenuActivity.setActivityTitle("오픈소스 고지")
    }

    private fun moveToVersionInfo() {
//        SodosiToast.makeText(this, "버전 정보", Toast.LENGTH_SHORT).show()
    }

    private fun moveToSodosiMakers() {
        val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/society.gwanak/"))
        startActivity(instagramIntent)
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
