package com.sodosi.ui.mypage

import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityMypageBinding
import com.sodosi.ui.common.base.BaseActivity

class MypageActivity : BaseActivity<MypageViewModel, ActivityMypageBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    override fun getViewBinding() = ActivityMypageBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
        initProfile()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initRightButton(R.drawable.ic_interface_settings_future) {

            }
        }
    }

    private fun initProfile() {
        binding.tvProfileNickname.text = "중구구립도서관"
        binding.tvHourAgo.text = "1시간 전 방문"
        binding.tvCreatedSodosiCount.text = "35"
        binding.tvJoinedSodosiCount.text = "35"
        binding.tvBookmarkCount.text = "35"
    }
}
