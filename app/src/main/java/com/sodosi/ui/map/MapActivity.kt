package com.sodosi.ui.map

import androidx.activity.viewModels
import com.skt.Tmap.TMapView
import com.sodosi.BuildConfig
import com.sodosi.databinding.ActivityMapBinding
import com.sodosi.ui.common.base.BaseActivity

class MapActivity : BaseActivity<MapViewModel, ActivityMapBinding>() {
    override fun getViewBinding() = ActivityMapBinding.inflate(layoutInflater)

    override val viewModel: MapViewModel by viewModels()

    override fun initViews() = with(binding) {
        initMapView()
    }

    override fun observeData() {

    }

    private fun initMapView() {
        val mapView = TMapView(this);
        mapView.setSKTMapApiKey(MAP_API_KEY)

        binding.mapContainer.addView(mapView)
    }

    companion object {
        private const val MAP_API_KEY = BuildConfig.TMAP_API_KEY
    }
}
