package com.sodosi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.R
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.databinding.ActivityMainBinding
import com.sodosi.domain.entity.Sodosi
import com.sodosi.ui.create.CreateActivity
import com.sodosi.ui.list.SodosiListActivity
import com.sodosi.ui.map.MapActivity
import com.sodosi.ui.mypage.MypageActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *  MainActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val bannerHandler: Handler by lazy { Handler() }
    private var runnable: Runnable? = null
    private var backPressWaitTime = 0L

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        runnable?.let { bannerHandler.postDelayed(it, SCROLL_DELAY_TIME) }
    }

    override fun onPause() {
        super.onPause()
        runnable?.let { bannerHandler.removeCallbacks(it) }
    }

    override fun onBackPressed() {
        when {
            System.currentTimeMillis() - backPressWaitTime >= 1500 -> {
                backPressWaitTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun initViews() = with(binding) {
        changeStatusBarColorWhite()

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
                    (binding.sodosiViewPager.adapter as SodosiViewPagerAdapter).submitList(
                        mainSodosiList
                    )
                    binding.sodosiViewPager.setCurrentItem(
                        (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % mainSodosiList.size),
                        false
                    )
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
                        moveToSodosiMap(it)
                    }
                }

            offscreenPageLimit = 1

            val pageMargin = resources.getDimension(R.dimen.main_viewpager_margin)
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = pageMargin * position
            }
            setPageTransformer(pageTransformer)

            runnable = Runnable {
                currentItem += 1
//                setCurrentItemWithDuration(currentItem + 1, SCROLL_DURATION_TIME)
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
                    moveToSodosiMap(it)
                }
            }
        }

        binding.rvNewSodosi.apply {
            adapter = SodosiAdapter().apply {
                onItemClick = {
                    moveToSodosiMap(it)
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivSodosiList.setOnClickListener {
            val intent = Intent(this, SodosiListActivity::class.java)
            startActivity(intent)
        }

        binding.ivMypage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.ivCreateSodosi.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        binding.footer.tvBlog.setOnClickListener {
            val blogIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FOOTER_URL_BLOG))
            startActivity(blogIntent)
        }

        binding.footer.tvInstagram.setOnClickListener {
            val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FOOGER_URL_INSTAGRAM))
            startActivity(instagramIntent)
        }

        binding.footer.ivScrollTop.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }
    }

    private fun moveToSodosiMap(sodosi: Sodosi) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("SODOSI_ID", sodosi.id)

        startActivity(intent)
    }

    companion object {
        private const val SCROLL_DELAY_TIME = 3000L
        private const val SCROLL_DURATION_TIME = 300L

        private const val FOOTER_URL_BLOG = "https://medium.com/@gwanaksociety"
        private const val FOOGER_URL_INSTAGRAM = ""
    }
}
