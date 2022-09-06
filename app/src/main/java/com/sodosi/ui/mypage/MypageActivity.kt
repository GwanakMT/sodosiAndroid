package com.sodosi.ui.mypage

import android.content.Intent
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityMypageBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : BaseActivity<MypageViewModel, ActivityMypageBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    override fun getViewBinding() = ActivityMypageBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.userBaseProfile.collect {
                initBaseProfile(it.first, it.second, it.third)
            }
        }
    }

    override fun initViews() = with(binding) {
        initAppbar()
        initUserSodosiInfo()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initRightButton(R.drawable.ic_interface_settings_future) {
                val settingIntent = Intent(this@MypageActivity, SettingActivity::class.java)
                startActivity(settingIntent)
            }
        }
    }

    private fun initBaseProfile(nickname: String, profileImage: String, lastVisitedTime: String) {
        binding.tvProfileNickname.text = nickname
        binding.tvHourAgo.text = getString(R.string.mypage_last_visited_time, lastVisitedTime)
    }

    private fun initUserSodosiInfo() {
        binding.tvCreatedSodosiCount.text = "35"
        binding.tvJoinedSodosiCount.text = "35"
        binding.tvBookmarkCount.text = "35"
    }
}
