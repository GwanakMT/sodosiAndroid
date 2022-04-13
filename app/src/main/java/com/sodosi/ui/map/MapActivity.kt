package com.sodosi.ui.map

import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapView
import com.sodosi.BuildConfig
import com.sodosi.databinding.ActivityMapBinding
import com.sodosi.ui.common.base.BaseActivity

class MapActivity : BaseActivity<MapViewModel, ActivityMapBinding>() {
    private lateinit var mapView: TMapView
    private val momentBottomSheet by lazy { MomentBottomSheetFragment() }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun getViewBinding() = ActivityMapBinding.inflate(layoutInflater)

    override val viewModel: MapViewModel by viewModels()

    override fun initViews() = with(binding) {
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
        supportFragmentManager.beginTransaction()
            .replace(binding.momentBottomSheetContainer.id, momentBottomSheet)
            .commitNow()

        bottomSheetBehavior = BottomSheetBehavior.from(binding.momentBottomSheetContainer)
        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 200
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun setOnClickListener() {
        binding.momentBottomSheetContainer.setOnClickListener {  }
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnBookmark.setOnClickListener {

        }
    }

    companion object {
        private const val MAP_API_KEY = BuildConfig.TMAP_API_KEY
    }
}
