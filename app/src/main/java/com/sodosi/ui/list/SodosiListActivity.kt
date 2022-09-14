package com.sodosi.ui.list

import android.graphics.Color
import android.graphics.Typeface
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiListBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.main.SodosiListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SodosiListActivity : BaseActivity<SodosiListViewModel, ActivitySodosiListBinding>() {
    override fun getViewBinding() = ActivitySodosiListBinding.inflate(layoutInflater)
    override val viewModel: SodosiListViewModel by viewModels()

    private val sodosiListAdapter = SodosiListAdapter()

    override fun initViews() = with(binding) {
        initAppbar()
        initRecyclerView()

        setOnClickListener()
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.inquirySodosiListSuccessEvent.collect {
                // TODO: stop progress bar
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
                        sodosiListAdapter.submitList(viewModel.sodosiListSortByRecent)

                        // selected
                        binding.tvSortRecent.typeface = Typeface.DEFAULT_BOLD
                        binding.tvSortRecent.setTextColor(Color.parseColor("#0F0F10"))

                        // unselected
                        binding.tvSortPopular.typeface = Typeface.DEFAULT
                        binding.tvSortPopular.setTextColor(Color.parseColor("#8A8A8E"))
                    }

                    SodosiListViewModel.SORT_BY_POPULAR -> {
                        sodosiListAdapter.submitList(viewModel.sodosiListSortByPopular)

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
                onItemClick = {}
                onBookmarkClick = {}
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
}
