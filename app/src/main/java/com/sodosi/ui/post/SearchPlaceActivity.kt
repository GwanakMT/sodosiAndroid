package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivitySearchPlaceBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setVisible
import kotlin.concurrent.timer

class SearchPlaceActivity : BaseActivity<SearchPlaceViewModel, ActivitySearchPlaceBinding>() {
    private var lastTypingTime: Long = System.currentTimeMillis()
    private var lastTypingText = ""

    override val viewModel: SearchPlaceViewModel by viewModels()
    private val searchResultAdapter by lazy { SearchResultAdapter() }

    override fun getViewBinding(): ActivitySearchPlaceBinding =
        ActivitySearchPlaceBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        initAppbar()
        initSearchBar()
        initRecyclerView()
        setListener()
    }

    override fun observeData() {
        repeatOnStarted {
            viewModel.poiList.collect {
                if (it.isNotEmpty()) {
                    binding.emptyLayout.root.setGone()
                    binding.searchResultRecyclerView.setVisible()

                    searchResultAdapter.submitList(it)
                } else {
                    binding.emptyLayout.root.setVisible()
                    binding.searchResultRecyclerView.setGone()
                }
            }
        }
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }
    }

    private fun initSearchBar() {
        binding.etPlace.addTextChangedListener {
            lastTypingTime = System.currentTimeMillis()
        }

        timer(period = 500, initialDelay = 500) {
            if (lastTypingTime < System.currentTimeMillis() - 500 && binding.etPlace.text.toString() != lastTypingText) {
                lastTypingText = binding.etPlace.text.toString()
                viewModel.search(lastTypingText)
            }
        }
    }

    private fun initRecyclerView() {
        binding.searchResultRecyclerView.apply {
            adapter = searchResultAdapter.apply {
                onItemClick = {
                    val sodosiId = intent.getLongExtra(KEY_SODOSI_ID, -1L)
                    if (sodosiId != -1L) {
                        startActivity(PostMomentActivity.getIntent(context, it, sodosiId))
                    }
                }
            }
        }
    }

    private fun setListener() {
        binding.etPlace.addTextChangedListener {
            if("$it".length > 0) {
                binding.ivClearText.setVisible()
            } else {
                binding.ivClearText.setGone()
            }
        }

        binding.ivClearText.setOnClickListener {
            binding.etPlace.text = null
        }
    }

    companion object {
        private val KEY_CURRENT_LONGITUDE = "KEY_CURRENT_LONGITUDE"
        private val KEY_CURRENT_LATITUDE = "KEY_CURRENT_LATITUDE"
        private val KEY_SODOSI_ID = "KEY_SODOSI_ID"

        fun getIntent(context: Context, currentLongitude: Double, currentLatitude: Double, sodosiId: Long): Intent {
            return Intent(context, SearchPlaceActivity::class.java).apply {
                putExtra(KEY_CURRENT_LONGITUDE, currentLongitude)
                putExtra(KEY_CURRENT_LATITUDE, currentLatitude)
                putExtra(KEY_SODOSI_ID, sodosiId)
            }
        }
    }
}
