package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sodosi.R
import com.sodosi.databinding.ActivityMySodosiListBinding
import com.sodosi.domain.Result
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.ui.create.CreateSodosiActivity
import com.sodosi.ui.list.SodosiListActivity
import com.sodosi.ui.main.SodosiListAdapter
import com.sodosi.ui.sodosi.SodosiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySodosiListActivity : BaseActivity<MypageViewModel, ActivityMySodosiListBinding>() {
    enum class MySodosiListType { CREATED, COMMENTED, MARKED, EDIT_MARKED }

    override fun getViewBinding() = ActivityMySodosiListBinding.inflate(layoutInflater)

    override val viewModel: MypageViewModel by viewModels()
    private val sodosiListAdapter by lazy { SodosiListAdapter() }

    private val createSodosiLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.getCreatedSodosiList()
        setResult(RESULT_OK)
    }

    private val bookmarkSodosiLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.getMarkedSodosiList()
        setResult(RESULT_OK)
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.sodosiList.collect {
                when(it) {
                    is Result.Success -> sodosiListAdapter.submitList(it.data)
                    is Result.Error -> {
                        SodosiToast.makeText(this@MySodosiListActivity, "데이터를 조회하는데 실패했습니다.. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    override fun initViews() {
        initRecyclerView()
        when(mySodosiListType) {
            MySodosiListType.CREATED -> initCreatedSodosiList(getString(R.string.mypage_created_sodosi))
            MySodosiListType.COMMENTED -> initCommentedSodosiList(getString(R.string.mypage_commented_sodosi))
            MySodosiListType.MARKED -> initMarkedSodosiList(getString(R.string.mypage_marked_sodosi))
            MySodosiListType.EDIT_MARKED -> initEditMarkedSodosiList(getString(R.string.mypage_marked_sodosi))
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = HorizontalItemDecoration(
            ContextCompat.getDrawable(this, R.drawable.horizontal_decoration) ?: return
        )

        binding.mySodosiList.apply {
            addItemDecoration(dividerItemDecoration)
            adapter = sodosiListAdapter.apply {
                itemViewType = SodosiListAdapter.ViewType.VERTICAL
                onItemClick = { sodosi ->
                    startActivity(SodosiActivity.getIntent(this@MySodosiListActivity, sodosi))
                }
            }
        }
    }

    private fun initCreatedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        viewModel.getCreatedSodosiList()
        binding.movePageText.text = getString(R.string.mypage_move_to_create)
        binding.movePageButton.setOnClickListener {
            createSodosiLauncher.launch(CreateSodosiActivity.getIntent(this))
            setResult(RESULT_OK)
        }
    }

    private fun initCommentedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        viewModel.getCommentedSodosiList()
        binding.movePageButton.setGone()
    }

    private fun initMarkedSodosiList(appbarTitle: String) {
        initAppbar(appbarTitle)
        viewModel.getMarkedSodosiList()
        binding.movePageText.text = getString(R.string.mypage_add_marked_sodosi)
        binding.movePageButton.setOnClickListener {
            bookmarkSodosiLauncher.launch(SodosiListActivity.getIntent(this))
            setResult(RESULT_OK)
        }
    }

    private fun initEditMarkedSodosiList(appbarTitle: String) {
        initMarkedSodosiList(appbarTitle)
        binding.tvEdit.setVisible()
        binding.tvEdit.setOnClickListener {
            bookmarkSodosiLauncher.launch(EditSodosiListActivity.getIntent(this))
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
