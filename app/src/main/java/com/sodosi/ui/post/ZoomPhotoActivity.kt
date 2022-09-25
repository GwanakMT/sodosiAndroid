package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.sodosi.databinding.ActivityZoomPhotoBinding
import com.sodosi.ui.common.base.BaseActivity

class ZoomPhotoActivity : BaseActivity<PostMomentViewModel, ActivityZoomPhotoBinding>() {
    override fun getViewBinding() = ActivityZoomPhotoBinding.inflate(layoutInflater)

    override val viewModel: PostMomentViewModel by viewModels()
    private val photoAdapter by lazy { ZoomPhotoAdapter() }

    override fun observeData() {

    }

    override fun initViews() {
        setListeners()
        setViewPager()
        setIndicator()
    }

    private fun setListeners() {
        binding.closeButton.setOnClickListener {
            finish()
        }
    }

    private fun setViewPager() {
        binding.photoViewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = photoAdapter
        }

        photoAdapter.submit(photoList)

        val position = intent.getIntExtra(EXTRA_PHOTO_POSITION, 0)
        binding.photoViewPager.setCurrentItem(position, false)
    }

    private fun setIndicator() {
        binding.indicator.attachTo(binding.photoViewPager)
    }

    companion object {
        private val EXTRA_PHOTO_POSITION = "EXTRA_PHOTO_POSITION"
        private var photoList = emptyList<Uri>()

        fun getIntent(context: Context, position: Int, photoList: List<Uri>): Intent {
            return Intent(context, ZoomPhotoActivity::class.java).apply {
                this@Companion.photoList = photoList
                putExtra(EXTRA_PHOTO_POSITION, position)
            }
        }
    }
}
