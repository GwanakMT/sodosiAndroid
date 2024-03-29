package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sodosi.R
import com.sodosi.databinding.ActivityEditSodosiListBinding
import com.sodosi.domain.Result
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.main.SodosiListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditSodosiListActivity : BaseActivity<MypageViewModel, ActivityEditSodosiListBinding>() {
    override val viewModel: MypageViewModel by viewModels()
    private val sodosiListAdapter by lazy { SodosiListAdapter() }
    private val checkList = HashMap<Long, Boolean>()

    override fun getViewBinding() = ActivityEditSodosiListBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.sodosiList.collect {
                when(it) {
                    is Result.Success -> sodosiListAdapter.submitList(it.data)
                    is Result.Error -> {
                        SodosiToast.makeText(this@EditSodosiListActivity, "데이터를 조회하는데 실패했습니다.. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.unmarkEvent.collect { isSuccess ->
                progress.dismiss()

                checkList.clear()
                updateAppBarTitleCount(0)

                viewModel.getMarkedSodosiList()

                if (!isSuccess) {
                    SodosiToast.makeText(this@EditSodosiListActivity, "작업 중간에 오류가 있었습니다. 결과를 확인해주세요", Toast.LENGTH_SHORT).show()
                }

                setResult(RESULT_OK)
            }
        }
    }

    override fun initViews() {
        initAppBar()
        initRecyclerView()

        viewModel.getMarkedSodosiList()

        setOnClickListener()
    }

    private fun initAppBar() {
        binding.appbar.initAppbarTitle(getString(R.string.mypage_edit_sodosi_list_title, "0"))
        binding.btnFinish.setOnClickListener {
            finish()
        }
    }

    private fun setOnClickListener() {
        binding.btnDelete.setOnClickListener {
            progress.show()

            val unmarkList = checkList.filter { it.value }.map { it.key }
            viewModel.unmarkSodosi(unmarkList)
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = HorizontalItemDecoration(
            ContextCompat.getDrawable(this, R.drawable.horizontal_decoration) ?: return
        )

        binding.sodosiList.apply {
            addItemDecoration(dividerItemDecoration)
            adapter = sodosiListAdapter.apply {
                itemViewType = SodosiListAdapter.ViewType.EDIT_MODE
                onItemChecked = { sodosi, isChceked ->
                    checkList.put(sodosi.id, isChceked)
                    updateAppBarTitleCount(checkList.count { it.value })
                }
            }
        }
    }

    private fun updateAppBarTitleCount(count: Int) {
        val title = getString(R.string.mypage_edit_sodosi_list_title, count.toString())
        binding.appbar.initAppbarTitle(title)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, EditSodosiListActivity::class.java)
        }
    }
}
