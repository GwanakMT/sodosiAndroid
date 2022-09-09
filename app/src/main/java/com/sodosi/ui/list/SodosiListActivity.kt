package com.sodosi.ui.list

import android.graphics.Color
import android.graphics.Typeface
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiListBinding
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SodosiListActivity : BaseActivity<SodosiListViewModel, ActivitySodosiListBinding>() {
    override val viewModel: SodosiListViewModel by viewModels()

    override fun getViewBinding() = ActivitySodosiListBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
        initRecyclerView()
        initSortTab()

        setOnClickListener()
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

    }

    private fun initSortTab() {

    }

    private fun setOnClickListener() {
        binding.tvSortPopular.setOnClickListener {
            // selected
            viewModel.sortBy()
            binding.tvSortPopular.typeface = Typeface.DEFAULT_BOLD
            binding.tvSortPopular.setTextColor(Color.parseColor("#0F0F10"))

            // unselected
            binding.tvSortFastest.typeface = Typeface.DEFAULT
            binding.tvSortFastest.setTextColor(Color.parseColor("#8A8A8E"))
        }

        binding.tvSortFastest.setOnClickListener {
            // selected
            viewModel.sortBy()
            binding.tvSortFastest.typeface = Typeface.DEFAULT_BOLD
            binding.tvSortFastest.setTextColor(Color.parseColor("#0F0F10"))

            // unselected
            binding.tvSortPopular.typeface = Typeface.DEFAULT
            binding.tvSortPopular.setTextColor(Color.parseColor("#8A8A8E"))
        }
    }
}
