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
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.setGone
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

    private val viewPagerAdapter by lazy { SodosiViewPagerAdapter() }
    private val commentedSodosiAdapter by lazy { SodosiListAdapter() }
    private val markedSodosiAdapter by lazy { SodosiListAdapter() }
    private val hotSodosiAdapter by lazy { SodosiListAdapter() }
    private val newSodosiAdapter by lazy { SodosiListAdapter() }

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
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.showSuggestBanner.collect {
                        setUiOfSuggestBanner(it)
                    }
                }

                launch {
                    viewModel.sodosiListsUpdatedEvent.collect { networkSuccess ->
                        if (networkSuccess) {
                            commentedSodosiAdapter.submitList(viewModel.commentedSodosiList)
                            markedSodosiAdapter.submitList(viewModel.mainSodosiList)
                            hotSodosiAdapter.submitList(viewModel.hotSodosiList)
                            newSodosiAdapter.submitList(viewModel.newSodosiList)

                            if (viewModel.mainSodosiList.isNotEmpty()) {
                                viewPagerAdapter.submitList(viewModel.mainSodosiList)

                                sodosiViewPagerListSize = viewModel.mainSodosiList.size
//                                binding.dotsIndicator.loadItems(sodosiViewPagerListSize, 0)
                                binding.sodosiViewPager.setCurrentItem(
                                    (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % sodosiViewPagerListSize),
                                    false
                                )
                            }
                        } else {
                            // Error View
                        }
                    }
                }

                launch {
                    viewModel.patchMarkSodosiEvent.collect { networkResult ->
                        if (networkResult) {
                            commentedSodosiAdapter.submitList(viewModel.commentedSodosiList)
                            markedSodosiAdapter.submitList(viewModel.mainSodosiList)
                            hotSodosiAdapter.submitList(viewModel.hotSodosiList)
                            newSodosiAdapter.submitList(viewModel.newSodosiList)
                        } else {
                            SodosiToast.makeText(this@MainActivity, "관심 소도시 등록/해제 실패...", Toast.LENGTH_SHORT).show()
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
                    onItemClick = ::moveToSodosi
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
//                        current == 0 -> binding.dotsIndicator.selectIndex(0)
//                        current > previous -> binding.dotsIndicator.next()
//                        current < previous -> binding.dotsIndicator.previous()
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

    private fun setUiOfSuggestBanner(showSuggestBanner: Boolean) {
        if (showSuggestBanner) {
            binding.suggestLayout.root.setVisible()
        } else {
            binding.suggestLayout.root.setGone()
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = HorizontalItemDecoration(
            ContextCompat.getDrawable(this, R.drawable.horizontal_decoration) ?: return
        )

        setCommentedSodosiList(dividerItemDecoration)
        setMarkedSodosiList()
        setHotSodosiList(dividerItemDecoration)
        setNewSodosiList(dividerItemDecoration)
    }

    private fun setOnClickListener() {
        // 뷰 클릭 이벤트 설정
        with(binding) {
            ivSodosiList.setOnClickListener {
                startActivity(SodosiListActivity.getIntent(this@MainActivity))
            }

            ivMypage.setOnClickListener {
                startActivity(MypageActivity.getIntent(this@MainActivity))
            }

            ivCreateSodosi.setOnClickListener {
                startActivity(CreateSodosiActivity.getIntent(this@MainActivity))
            }
        }

        // 상단 배너 뷰 클릭 이벤트 설정
        with(binding.suggestLayout) {
            btnCreateSodosi.setOnClickListener {
                viewModel.setSuggestBannerHide()
                startActivity(CreateSodosiActivity.getIntent(this@MainActivity))
            }

            btnCancel.setOnClickListener {
                viewModel.setSuggestBannerHide()
                root.animate()
                    .alpha(0.0f)
                    .translationY(-it.height.toFloat())
                    .duration = 1000L

                binding.homeContainer.animate()
                    .translationY(-(root.height.toFloat() + root.marginTop))
                    .duration = 1000L
            }
        }

        // 푸터 뷰 클릭 이벤트 설정
        with(binding.footer) {
            tvBlog.setOnClickListener {
                val blogIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FOOTER_URL_BLOG))
                startActivity(blogIntent)
            }

            tvInstagram.setOnClickListener {
                val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FOOGER_URL_INSTAGRAM))
                startActivity(instagramIntent)
            }

            tvCoffee.setOnClickListener {
                val coffeeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FOOGER_URL_COFFEE))
                startActivity(coffeeIntent)
            }

            ivScrollTop.setOnClickListener {
                binding.scrollView.smoothScrollTo(0, 0)
            }
        }
    }

    private fun setCommentedSodosiList(divider: HorizontalItemDecoration) {
        binding.rvCommentedSodosi.apply {
            adapter = commentedSodosiAdapter.apply {
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::toggleBookmark
            }

            addItemDecoration(divider)
        }
    }

    private fun setMarkedSodosiList() {
        binding.rvBookmarkSodosi.apply {
            adapter = markedSodosiAdapter.apply {
                itemViewType = SodosiListAdapter.ViewType.HORIZONTAL
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::toggleBookmark
            }
        }
    }

    private fun setHotSodosiList(divider: HorizontalItemDecoration) {
        binding.rvHotSodosi.apply {
            adapter = hotSodosiAdapter.apply {
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::toggleBookmark
            }

            addItemDecoration(divider)
        }
    }

    private fun setNewSodosiList(divider: HorizontalItemDecoration) {
        binding.rvNewSodosi.apply {
            adapter = newSodosiAdapter.apply {
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::toggleBookmark
            }

            addItemDecoration(divider)
        }
    }

    private fun moveToSodosi(sodosi: SodosiModel) {
        startActivity(SodosiActivity.getIntent(this, sodosi.id, sodosi.name, sodosi.momentCount))
    }

    private fun toggleBookmark(sodosi: SodosiModel) {
        viewModel.patchMarkSodosi(sodosi.id, sodosi.isMarked)
    }

    companion object {
        private const val BACKPRESS_DELAY_TIME = 1500
        private const val SCROLL_DELAY_TIME = 3000L
        private const val SCROLL_DURATION_TIME = 300L

        private const val FOOTER_URL_BLOG = "https://medium.com/@gwanaksociety"
        private const val FOOGER_URL_INSTAGRAM = "https://www.instagram.com/society.gwanak/"
        private const val FOOGER_URL_COFFEE = "https://www.instagram.com/society.gwanak/" // FIXME
    }
}
