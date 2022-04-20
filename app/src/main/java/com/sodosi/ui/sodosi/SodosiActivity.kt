package com.sodosi.ui.sodosi

import android.graphics.Point
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapView
import com.sodosi.BuildConfig
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiBinding
import com.sodosi.ui.common.base.BaseActivity

class SodosiActivity : BaseActivity<SodosiViewModel, ActivitySodosiBinding>() {
    private lateinit var mapView: TMapView
    private val momentBottomSheet by lazy { MomentBottomSheetFragment() }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun getViewBinding() = ActivitySodosiBinding.inflate(layoutInflater)

    override val viewModel: SodosiViewModel by viewModels()

    override fun initViews() = with(binding) {
        changeStatusBarColorWhite()
        binding.tvMapTitle.text = intent.getStringExtra(EXTRA_MAP_NAME)
        binding.tvMomentCount.text = getString(R.string.sodosi_moment_count, intent.getIntExtra(EXTRA_MOMENT_COUNT, 0).toString())

        initMapView()
        initMomentBottomSheet()
        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun initMapView() {
        mapView = TMapView(this);
        mapView.setSKTMapApiKey(MAP_API_KEY)

        binding.mapContainer.addView(mapView)
    }

    private fun initMomentBottomSheet() {
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)

        supportFragmentManager.beginTransaction()
            .replace(binding.momentBottomSheetContainer.id, momentBottomSheet)
            .commitNow()

        bottomSheetBehavior = BottomSheetBehavior.from(binding.momentBottomSheetContainer)
        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = resources.getDimensionPixelSize(R.dimen.moment_bottom_sheet_peek_height)
            maxHeight = (size.y - resources.getDimensionPixelSize(R.dimen.moment_bottom_sheet_not_include_max_height))

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun setOnClickListener() {
        binding.momentBottomSheetContainer.setOnClickListener { }
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnMenu.setOnClickListener {

        }
    }

    companion object {
        const val MAP_API_KEY = BuildConfig.TMAP_API_KEY

        const val EXTRA_MAP_ID = "EXTRA_MAP_ID"
        const val EXTRA_MAP_NAME = "EXTRA_MAP_NAME"
        const val EXTRA_MOMENT_COUNT = "EXTRA_MOMENT_COUNT"
    }
}
