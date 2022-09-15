package com.sodosi.ui.list

import android.graphics.Color
import android.graphics.Typeface
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiListBinding
import com.sodosi.domain.Result
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.main.SodosiListAdapter
import com.sodosi.ui.sodosi.SodosiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SodosiListActivity : BaseActivity<SodosiListViewModel, ActivitySodosiListBinding>() {
    override fun getViewBinding() = ActivitySodosiListBinding.inflate(layoutInflater)
    override val viewModel: SodosiListViewModel by viewModels()

    private val sodosiListAdapter = SodosiListAdapter()

    override fun initViews() = with(binding) {
        progress.show()

        initAppbar()
        initRecyclerView()

        setOnClickListener()
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.inquirySodosiListSuccessEvent.collect {
                progress.dismiss()
                viewModel.sodosiListSortByPopular?.let { sodosiListAdapter.submitList(it) }
            }
        }

        repeatOnStarted {
            viewModel.inquirySodosiListErrorEvent.collect {
                // TODO: 에러 다이얼로그 & finish
            }
        }

        repeatOnStarted {
            viewModel.currentTab.collect {
                when (it) {
                    SodosiListViewModel.SORT_BY_RECENT -> {
                        sodosiListAdapter.submitList(viewModel.sodosiListSortByRecent) {
                            binding.sodosiList.smoothScrollToPosition(0)
                        }

                        // selected
                        binding.tvSortRecent.typeface = Typeface.DEFAULT_BOLD
                        binding.tvSortRecent.setTextColor(Color.parseColor("#0F0F10"))

                        // unselected
                        binding.tvSortPopular.typeface = Typeface.DEFAULT
                        binding.tvSortPopular.setTextColor(Color.parseColor("#8A8A8E"))
                    }

                    SodosiListViewModel.SORT_BY_POPULAR -> {
                        sodosiListAdapter.submitList(viewModel.sodosiListSortByPopular) {
                            binding.sodosiList.smoothScrollToPosition(0)
                        }

                        // selected
                        binding.tvSortPopular.typeface = Typeface.DEFAULT_BOLD
                        binding.tvSortPopular.setTextColor(Color.parseColor("#0F0F10"))

                        // unselected
                        binding.tvSortRecent.typeface = Typeface.DEFAULT
                        binding.tvSortRecent.setTextColor(Color.parseColor("#8A8A8E"))
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.updateMarkStateEvent.collect {
                when (it) {
                    is Result.Success -> {
                        when (viewModel.currentTab.value) {
                            SodosiListViewModel.SORT_BY_RECENT -> {
                                sodosiListAdapter.submitList(viewModel.sodosiListSortByRecent) 
                            }
                            SodosiListViewModel.SORT_BY_POPULAR -> {
                                sodosiListAdapter.submitList(viewModel.sodosiListSortByPopular)
                            }
                        }
                    }

                    is Result.Error -> {
                        SodosiToast.makeText(this@SodosiListActivity, "관심 소도시 등록/해제 실패...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.sodosi_total_list_title))
        }
    }

    private fun initRecyclerView() {
        binding.sodosiList.apply {
            adapter = sodosiListAdapter.apply {
                itemViewType = SodosiListAdapter.ViewType.VERTICAL
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::onBookmarkClick
            }
        }
    }

    private fun setOnClickListener() {
        binding.tvSortPopular.setOnClickListener {
            viewModel.setCurrentTab(SodosiListViewModel.SORT_BY_POPULAR)
        }

        binding.tvSortRecent.setOnClickListener {
            viewModel.setCurrentTab(SodosiListViewModel.SORT_BY_RECENT)
        }
    }

    private fun moveToSodosi(sodosi: SodosiModel) {
        val intent = SodosiActivity.getIntent(
            context = this,
            id = sodosi.id,
            name = sodosi.name,
            momentCount = sodosi.momentCount
        )

        startActivity(intent)
    }

    private fun onBookmarkClick(sodosi: SodosiModel) {
        viewModel.patchMarkSodosi(sodosi.id, sodosi.isMarked)
    }
}
