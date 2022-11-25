package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.sodosi.R
import com.sodosi.databinding.ActivityMypageBinding
import com.sodosi.domain.Result
import com.sodosi.domain.entity.User
import com.sodosi.ui.comment.SodosiCommentActivity
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.post.ZoomPhotoActivity
import com.sodosi.ui.setting.SettingActivity
import com.sodosi.ui.sodosi.adapter.MomentListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : BaseActivity<MypageViewModel, ActivityMypageBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    private val momentAdpater by lazy { MomentListAdapter() }

    private val changeNickNameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getUserBaseProfile()
        }
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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

        repeatOnStarted {
            viewModel.myMomentList.collect {
                when(it) {
                    is Result.Success -> momentAdpater.submitList(it.data)
                    is Result.Error -> {} // TODO: set error view
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        initAppbar()
        initMomentList()

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

    private fun initMomentList() {
        binding.myMomentList.apply {
            adapter = momentAdpater.apply {
                onItemClick = { selectedItem ->
                    val intent = SodosiCommentActivity.getIntent(this@MypageActivity, selectedItem)
                    startActivity(intent)
                }

                onPhotoClick = { imageUrlList, position ->
                    val intent = ZoomPhotoActivity.getIntent(this@MypageActivity, position, imageUrlList.map { it.toUri() })
                    startActivity(intent)
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.tvProfileNickname.setOnClickListener {
            changeNickNameLauncher.launch(EditNickNameActivity.getIntent(this, binding.tvProfileNickname.text.toString()))
        }

        binding.tvCreatedSodosiCount.setOnClickListener {
            activityResultLauncher.launch(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.CREATED))
            setResult(RESULT_OK)
        }

        binding.tvCommentedSodosiCount.setOnClickListener {
            activityResultLauncher.launch(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.COMMENTED))
        }

        binding.tvBookmarkCount.setOnClickListener {
            activityResultLauncher.launch(MySodosiListActivity.getIntent(this, MySodosiListActivity.MySodosiListType.MARKED))
            setResult(RESULT_OK)
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MypageActivity::class.java)
        }
    }
}
