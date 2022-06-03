package com.sodosi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.R
import com.sodosi.databinding.ActivityMainBinding
import com.sodosi.domain.entity.Sodosi
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.ui.create.CreateSodosiActivity
import com.sodosi.ui.list.SodosiListActivity
import com.sodosi.ui.mypage.MypageActivity
import com.sodosi.ui.sodosi.SodosiActivity
import dagger.hilt.android.AndroidEntryPoint
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

    private var sodosiViewPagerListSize = 0

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
            System.currentTimeMillis() - backPressWaitTime >= BACKPRESS_DELAY_TIME -> {
                backPressWaitTime = System.currentTimeMillis()
                SodosiToast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun initViews() = with(binding) {
        changeStatusBarColorWhite()

        initViewPager()
        initRecyclerView()
        setOnClickListener()

        viewModel.setSodosiList()
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.bannerVisibleOrGone.collect { flag ->
                        if (flag) setUiOfSuggestBanner()
                    }
                }

                launch {
                    viewModel.listUpdated.collect {
                        with(viewModel) {
                            if (mainSodosiList.isNotEmpty()) {
                                (binding.sodosiViewPager.adapter as SodosiViewPagerAdapter).submitList(mainSodosiList)

                                sodosiViewPagerListSize = mainSodosiList.size
                                binding.dotsIndicator.loadItems(mainSodosiList.size, 0)
                                binding.sodosiViewPager.setCurrentItem(
                                    (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % mainSodosiList.size),
                                    false
                                )

                                (binding.rvCommentedSodosi.adapter as SodosiListAdapter).submitList(commentedSodosiList)
                                (binding.rvBookmarkSodosi.adapter as SodosiListAdapter).submitList(bookmarkSodosiList)
                                (binding.rvHotSodosi.adapter as SodosiListAdapter).submitList(hotSodosiList)
                                (binding.rvNewSodosi.adapter as SodosiListAdapter).submitList(newSodosiList)
                            }
                        }
                    }
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

            var previous = 0
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position % ((this@apply).adapter as SodosiViewPagerAdapter).itemCount)

                    val current: Int = position % sodosiViewPagerListSize
                    when {
                        current == 0 -> binding.dotsIndicator.selectIndex(0)
                        current > previous -> binding.dotsIndicator.next()
                        current < previous -> binding.dotsIndicator.previous()
                    }
                    previous = current

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

    private fun setUiOfSuggestBanner() {
        with(binding.suggestLayout) {
            btnCreateSodosi.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateSodosiActivity::class.java)
                startActivity(intent)
            }

            btnCancel.setOnClickListener {
                root.animate()
                    .alpha(0.0f)
                    .translationY(-it.height.toFloat())
                    .duration = 1000L

                binding.homeContainer.animate()
                    .translationY(-(root.height.toFloat() + root.marginTop))
                    .duration = 1000L

                viewModel.setBannerShowFlagFalse()
            }

            root.setVisible()
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = HorizontalItemDecoration(
            ContextCompat.getDrawable(this, R.drawable.horizontal_decoration) ?: return
        )

        binding.rvCommentedSodosi.apply {
            adapter = SodosiListAdapter().apply {
                onItemClick = {
                    moveToSodosiMap(it)
                }
                onBookmarkClick = {
                    toggleBookmark(it)
                }
            }

            addItemDecoration(dividerItemDecoration)
        }

        binding.rvBookmarkSodosi.apply {
            adapter = SodosiListAdapter().apply {
                itemViewType = SodosiListAdapter.ViewType.HORIZONTAL
                onItemClick = {
                    moveToSodosiMap(it)
                }
                onBookmarkClick = {
                    toggleBookmark(it)
                }
            }
        }

        binding.rvHotSodosi.apply {
            adapter = SodosiListAdapter().apply {
                onItemClick = {
                    moveToSodosiMap(it)
                }
                onBookmarkClick = {
                    toggleBookmark(it)
                }
            }

            addItemDecoration(dividerItemDecoration)
        }

        binding.rvNewSodosi.apply {
            adapter = SodosiListAdapter().apply {
                onItemClick = {
                    moveToSodosiMap(it)
                }
                onBookmarkClick = {
                    toggleBookmark(it)
                }
            }

            addItemDecoration(dividerItemDecoration)
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
            val intent = Intent(this, CreateSodosiActivity::class.java)
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
        val intent = Intent(this, SodosiActivity::class.java)
        intent.putExtra(SodosiActivity.EXTRA_MAP_ID, sodosi.id)
        intent.putExtra(SodosiActivity.EXTRA_MAP_NAME, sodosi.name)
        intent.putExtra(SodosiActivity.EXTRA_MOMENT_COUNT, sodosi.momentCount)

        startActivity(intent)
    }

    private fun toggleBookmark(sodosi: Sodosi) {

    }

    companion object {
        private const val BACKPRESS_DELAY_TIME = 1500
        private const val SCROLL_DELAY_TIME = 3000L
        private const val SCROLL_DURATION_TIME = 300L

        private const val FOOTER_URL_BLOG = "https://medium.com/@gwanaksociety"
        private const val FOOGER_URL_INSTAGRAM = "https://www.instagram.com/society.gwanak/"
    }
}
