package com.sodosi.ui.home

import android.content.Intent
import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.databinding.FragmentHomeBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.extensions.setCurrentItemWithDuration
import com.sodosi.ui.list.SodosiListActivity
import com.sodosi.ui.map.MapActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val bannerHandler: Handler by lazy { Handler() }
    private var runnable: Runnable? = null

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override val viewModel: HomeViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        runnable?.let { bannerHandler.postDelayed(it, SCROLL_DELAY_TIME) }
    }

    override fun onPause() {
        super.onPause()
        runnable?.let { bannerHandler.removeCallbacks(it) }
    }

    override fun initViews() = with(binding) {
        setOnClickListener()
        initViewPager()
        initSodosiRecyclerView()

        viewModel.getMainSodosiList()
        viewModel.getHotSodosiList()
        viewModel.getNewSodosiList()
    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.mainSodosiList.collect { mainSodosiList ->
                    (binding.sodosiViewPager.adapter as SodosiViewPagerAdapter).submitList(mainSodosiList)
                    binding.sodosiViewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % mainSodosiList.size), false)
                }
            }

            launch {
                viewModel.hotSodosiList.collect { hotSodosiList ->
                    (binding.rvHotSodosi.adapter as SodosiAdapter).submitList(hotSodosiList)
                }
            }

            launch {
                viewModel.newSodosiList.collect { newSodosiList ->
                    (binding.rvNewSodosi.adapter as SodosiAdapter).submitList(newSodosiList)
                }
            }
        }
    }

    private fun initViewPager() {
        binding.sodosiViewPager.apply {
            adapter = SodosiViewPagerAdapter()
                .apply {
                    onItemClick = {
                        val intent = Intent(context, MapActivity::class.java)
                        startActivity(intent)
                    }
                }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            runnable = Runnable {
                setCurrentItemWithDuration(currentItem + 1, SCROLL_DURATION_TIME)
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position % ((this@apply).adapter as SodosiViewPagerAdapter).itemCount)

                    runnable?.let {
                        bannerHandler.removeCallbacks(it)
                        bannerHandler.postDelayed(it, SCROLL_DELAY_TIME)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            runnable?.let {
                                bannerHandler.removeCallbacks(it)
                                bannerHandler.postDelayed(it, SCROLL_DELAY_TIME)
                            }
                        }

                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            runnable?.let {
                                bannerHandler.removeCallbacks(it)
                            }
                        }

                        ViewPager2.SCROLL_STATE_SETTLING -> {
                            // do nothing
                        }
                    }
                }
            })
        }
    }

    private fun initSodosiRecyclerView() {
        binding.rvHotSodosi.apply {
            adapter = SodosiAdapter().apply {
                onItemClick = {

                }
            }
        }

        binding.rvNewSodosi.apply {
            adapter = SodosiAdapter().apply {
                onItemClick = {

                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivSodosiList.setOnClickListener {
            val intent = Intent(context, SodosiListActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val SCROLL_DELAY_TIME = 3000L
        private const val SCROLL_DURATION_TIME = 300L
    }
}
