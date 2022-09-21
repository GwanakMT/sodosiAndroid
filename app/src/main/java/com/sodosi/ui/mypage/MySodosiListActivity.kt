package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityMySodosiListBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.create.CreateSodosiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySodosiListActivity : BaseActivity<MypageViewModel, ActivityMySodosiListBinding>() {
    enum class MySodosiListType { CREATED, COMMENTED, MARKED }

    override val viewModel: MypageViewModel by viewModels()

    override fun getViewBinding() = ActivityMySodosiListBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        when(mySodosiListType) {
            MySodosiListType.CREATED -> initCreatedSodosiList(getString(R.string.mypage_created_sodosi))
            MySodosiListType.COMMENTED -> initCommentedSodosiList(getString(R.string.mypage_commented_sodosi))
            MySodosiListType.MARKED -> initMarkedSodosiList(getString(R.string.mypage_marked_sodosi))
        }
    }

    private fun initCreatedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        binding.movePageText.text = getString(R.string.mypage_move_to_create)
        binding.movePageButton.setOnClickListener {
            startActivity(CreateSodosiActivity.getIntent(this))
        }
    }

    private fun initCommentedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        binding.movePageButton.setGone()
    }

    private fun initMarkedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        binding.movePageText.text = getString(R.string.mypage_add_marked_sodosi)
        binding.movePageButton.setOnClickListener {

        }
    }

    private fun initAppbar(appbarTitle: String) {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(appbarTitle)
        }
    }

    companion object {
        private var mySodosiListType: MySodosiListType? = null

        fun getIntent(context: Context, listType: MySodosiListType): Intent {
            mySodosiListType = listType
            return Intent(context, MySodosiListActivity::class.java)
        }
    }
}
