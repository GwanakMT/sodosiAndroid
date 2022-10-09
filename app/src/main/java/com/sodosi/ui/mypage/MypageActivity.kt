package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityMypageBinding
import com.sodosi.domain.Result
import com.sodosi.domain.entity.User
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : BaseActivity<MypageViewModel, ActivityMypageBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    private val changeNickNameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getUserBaseProfile()
        }
    }

    override fun getViewBinding() = ActivityMypageBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.userBaseProfile.collect {
                when(it) {
                    is Result.Success -> initBaseProfile(it.data.first, it.data.second)
                    is Result.Error -> {
                        SodosiToast.makeText(binding.root.context, "내 정보를 가져오는데 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()
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

    private fun initBaseProfile(user: User, lastVisitedTime: String) {
        binding.tvProfileNickname.text = user.nickName
        binding.tvHourAgo.text = getString(R.string.mypage_last_visited_time, lastVisitedTime)

        binding.tvCreatedSodosiCount.text = user.madeSodosiCount.toString()
        binding.tvCommentedSodosiCount.text = user.participateSodosiCount.toString()
        binding.tvBookmarkCount.text = user.bookmarkSodosiCount.toString()
    }

    private fun setOnClickListener() {
        binding.tvProfileNickname.setOnClickListener {
            changeNickNameLauncher.launch(EditNickNameActivity.getIntent(this, binding.tvProfileNickname.text.toString()))
        }

        binding.tvCreatedSodosiCount.setOnClickListener {
            startActivity(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.CREATED))
        }

        binding.tvCommentedSodosiCount.setOnClickListener {
            startActivity(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.COMMENTED))
        }

        binding.tvBookmarkCount.setOnClickListener {
            startActivity(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.MARKED))
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MypageActivity::class.java)
        }
    }
}
