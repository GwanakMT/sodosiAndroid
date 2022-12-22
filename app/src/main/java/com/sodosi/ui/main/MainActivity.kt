package com.sodosi.ui.main

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.R
import com.sodosi.databinding.ActivityMainBinding
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.heightAnimation
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.ui.create.CreateSodosiActivity
import com.sodosi.ui.list.SodosiListActivity
import com.sodosi.ui.mypage.MySodosiListActivity
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

    private var hotSodosiCollapseState = true
    private var newSodosiCollapseState = true

    private var sodosiViewPagerListSize = 0

    private val viewPagerAdapter by lazy { SodosiViewPagerAdapter() }
    private val fakeViewPagerAdapter by lazy { FakeViewPagerAdapter() }

    private val commentedSodosiAdapter by lazy { SodosiListAdapter() }
    private val markedSodosiAdapter by lazy { SodosiListAdapter() }
    private val hotSodosiAdapter by lazy { SodosiListAdapter() }
    private val newSodosiAdapter by lazy { SodosiListAdapter() }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getMainSodosiList()
        }
    }

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
                            if (viewModel.commentedSodosiList.isEmpty()) {
                                binding.tvJoinSodosiTitle.setGone()
                                binding.rvCommentedSodosi.setGone()
                                binding.dividerCommentedSodosi.setGone()
                            } else {
                                binding.tvJoinSodosiTitle.setVisible()
                                binding.rvCommentedSodosi.setVisible()
                                binding.dividerCommentedSodosi.setVisible()

                                commentedSodosiAdapter.submitList(viewModel.commentedSodosiList)
                            }

                            if (viewModel.bookmarkSodosiList.isEmpty()) {
                                binding.bookmarkSodosiContainer.setGone()
                                binding.rvBookmarkSodosi.setGone()
                                binding.dividerBookmarkSodosi.setGone()
                            } else {
                                binding.bookmarkSodosiContainer.setVisible()
                                binding.rvBookmarkSodosi.setVisible()
                                binding.dividerBookmarkSodosi.setVisible()

                                markedSodosiAdapter.submitList(viewModel.bookmarkSodosiList)
                            }

                            if (viewModel.mainSodosiList.isNotEmpty()) {
                                val beforeList = viewPagerAdapter.currentList

                                viewPagerAdapter.submitList(viewModel.mainSodosiList)
                                fakeViewPagerAdapter.submitList(viewModel.mainSodosiList)

                                if (beforeList.map { it.id } != viewModel.mainSodosiList.map { it.id }) {
                                    sodosiViewPagerListSize = viewModel.mainSodosiList.size
                                    binding.fakeViewPager.setCurrentItem(0, false)
                                    binding.sodosiViewPager.setCurrentItem(
                                        (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % sodosiViewPagerListSize),
                                        false
                                    )
                                }
                            }
                        } else {
                            // Error View
                        }
                    }
                }

                launch {
                    viewModel.hotSodosiListUpdatedEvent.collect {
                        if (viewModel.hotSodosiList.isEmpty()) {
                            binding.tvHotSodosiTitle.setGone()
                            binding.rvHotSodosi.setGone()
                            binding.btnMoreHotSodosi.setGone()
                            binding.dividerHotSodosi.setGone()
                        } else {
                            binding.tvHotSodosiTitle.setVisible()
                            binding.rvHotSodosi.setVisible()
                            binding.btnMoreHotSodosi.setVisible()
                            binding.dividerHotSodosi.setVisible()

                            hotSodosiAdapter.submitList(viewModel.hotSodosiList) {
                                binding.rvHotSodosi.scrollToPosition(0)
                            }
                        }
                    }
                }

                launch {
                    viewModel.newSodosiListUpdatedEvent.collect {
                        if (viewModel.newSodosiList.isEmpty()) {
                            binding.tvNewSodosiTitle.setGone()
                            binding.rvNewSodosi.setGone()
                            binding.tvMoreNewSodosi.setGone()
                        } else {
                            binding.tvNewSodosiTitle.setVisible()
                            binding.rvNewSodosi.setVisible()
                            binding.tvMoreNewSodosi.setVisible()

                            newSodosiAdapter.submitList(viewModel.newSodosiList)
                        }
                    }
                }

                launch {
                    viewModel.patchMarkSodosiEvent.collect { networkResult ->
                        if (networkResult) {
                            viewModel.getMainSodosiList()
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
            adapter = viewPagerAdapter.apply {
                onItemClick = ::moveToSodosi
                onBookMarkClick = ::toggleBookmark
            }

            offscreenPageLimit = 1

            val pageMargin = resources.getDimension(R.dimen.main_viewpager_margin)
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = pageMargin * position
            }
            setPageTransformer(pageTransformer)

            runnable = Runnable {
                setCurrentItem(currentItem + 1, true)
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position % viewPagerAdapter.itemCount)

                    val realPosition = position % viewPagerAdapter.currentList.size
                    when(realPosition) {
                        1 -> binding.indicator.selectedDotColor = Color.parseColor("#FFCC00")
                        2 -> binding.indicator.selectedDotColor = Color.parseColor("#5856D6")
                        3 -> binding.indicator.selectedDotColor = Color.parseColor("#FF9500")
                        else -> binding.indicator.selectedDotColor = Color.parseColor("#01AD00")
                    }

                    binding.fakeViewPager.setCurrentItem(realPosition, true)

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

        binding.fakeViewPager.apply {
            adapter = fakeViewPagerAdapter
        }

        binding.indicator.attachTo(binding.fakeViewPager)
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
                activityResultLauncher.launch(SodosiListActivity.getIntent(this@MainActivity))
            }

            ivMypage.setOnClickListener {
                activityResultLauncher.launch(MypageActivity.getIntent(this@MainActivity))
            }

            ivCreateSodosi.setOnClickListener {
                activityResultLauncher.launch(CreateSodosiActivity.getIntent(this@MainActivity))
            }

            tvEditMarkedSodosi.setOnClickListener {
                activityResultLauncher.launch(MySodosiListActivity.getIntent(this@MainActivity, MySodosiListActivity.MySodosiListType.EDIT_MARKED))
            }

            btnMoreHotSodosi.setOnClickListener {
                val itemHeight = resources.getDimensionPixelSize(R.dimen.item_sodosi_height)
                val hotSodosiMaxSize = viewModel.hotSodosiList.size

                if (hotSodosiCollapseState) {
                    // 접혀있으면
                    binding.rvHotSodosi.heightAnimation(itemHeight * hotSodosiMaxSize)
                    binding.btnMoreHotSodosi.text = getString(R.string.collapse_sodosi)
                    hotSodosiCollapseState = false
                } else {
                    // 펼쳐져 있었으면
                    binding.rvHotSodosi.heightAnimation(itemHeight * 4)
                    hotSodosiCollapseState = true
                    binding.btnMoreHotSodosi.text = getString(R.string.expand_sodosi)
                }

                binding.rvHotSodosi.scrollToPosition(0)
            }

            tvMoreNewSodosi.setOnClickListener {
                val itemHeight = resources.getDimensionPixelSize(R.dimen.item_sodosi_height)
                val hotSodosiMaxSize = viewModel.newSodosiList.size

                if (newSodosiCollapseState) {
                    // 접혀있으면
                    binding.rvNewSodosi.heightAnimation(itemHeight * hotSodosiMaxSize)
                    newSodosiCollapseState = false
                    binding.tvMoreNewSodosi.setGone() // TODO: 얘는 처리가 어떻게 되는거지
                } else {
                    // 펼쳐져 있었으면
                    binding.rvNewSodosi.heightAnimation(itemHeight * 6)
                    newSodosiCollapseState = true
                }
            }
        }

        // 상단 배너 뷰 클릭 이벤트 설정
        with(binding.suggestLayout) {
            btnCreateSodosi.setOnClickListener {
                viewModel.setSuggestBannerHide()
                activityResultLauncher.launch(CreateSodosiActivity.getIntent(this@MainActivity))
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
                showRank = true
            }

            layoutManager = object: LinearLayoutManager(this@MainActivity) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val itemHeight = resources.getDimensionPixelSize(R.dimen.item_sodosi_height)
            heightAnimation(itemHeight * 4, 0L)
            hotSodosiCollapseState = true
        }

        hotSodosiCollapseState = true
    }

    private fun setNewSodosiList(divider: HorizontalItemDecoration) {
        binding.rvNewSodosi.apply {
            adapter = newSodosiAdapter.apply {
                onItemClick = ::moveToSodosi
                onBookmarkClick = ::toggleBookmark
            }

            layoutManager = object: LinearLayoutManager(this@MainActivity) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val itemHeight = resources.getDimensionPixelSize(R.dimen.item_sodosi_height)
            heightAnimation(itemHeight * 6, 0L)
            newSodosiCollapseState = true

            addItemDecoration(divider)
        }
    }

    private fun moveToSodosi(sodosi: SodosiModel) {
        activityResultLauncher.launch(SodosiActivity.getIntent(this, sodosi))
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
