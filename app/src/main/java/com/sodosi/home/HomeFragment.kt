package com.sodosi.home

import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.R
import com.sodosi.common.base.BaseFragment
import com.sodosi.databinding.FragmentHomeBinding
import com.sodosi.location.LocationActivity
import com.sodosi.main.MainActivity
import com.sodosi.map.MapFragment

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

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

        viewModel.getMapPreviewList()
    }

    override fun observeData() {
        viewModel.mapPreviewList.asLiveData().observe(viewLifecycleOwner) {
            (binding.mapViewPager.adapter as MapViewPagerAdapter).setItem(it)
        }
    }

    private fun initViewPager() {
        binding.mapViewPager.apply {
            adapter = MapViewPagerAdapter()
                .apply {
                    onItemClick = {
                        (activity as MainActivity).changeFragment(MapFragment())
                    }
                }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val cardOffset = resources.getDimension(R.dimen.main_viewpager_offset)
            val nextItemVisiblePx = resources.getDimension(R.dimen.main_viewpager_margin)
            val pageTranslationX = (cardOffset * VIEWPAGER_PRE_VIEW) + nextItemVisiblePx

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State,
                ) {
                    outRect.right = cardOffset.toInt() + nextItemVisiblePx.toInt()
                    outRect.left = cardOffset.toInt() + nextItemVisiblePx.toInt()
                }
            })

            setPageTransformer { page, position ->
                page.translationX = -pageTranslationX * (position)
            }

            runnable = Runnable {
                currentItem += 1
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position % ((this@apply).adapter as MapViewPagerAdapter).itemCount)

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

    private fun setOnClickListener() {
        binding.tvLocation.setOnClickListener {
            val intent = Intent(context, LocationActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val VIEWPAGER_PRE_VIEW = 3
        private const val SCROLL_DELAY_TIME = 3000L
    }
}
