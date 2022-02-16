package com.github.sookhee.sodosi.map

import androidx.fragment.app.viewModels
import com.github.sookhee.sodosi.BuildConfig
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentMapBinding
import com.github.sookhee.sodosi.home.HomeViewModel
import com.skt.Tmap.TMapView

/**
 *  MapFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/16
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MapFragment : BaseFragment<HomeViewModel, FragmentMapBinding>() {
    override fun getViewBinding() = FragmentMapBinding.inflate(layoutInflater)

    override val viewModel: HomeViewModel by viewModels()

    override fun initViews() = with(binding) {
        initMapView()
    }

    override fun observeData() {

    }

    private fun initMapView() {
        val mapView = TMapView(context);
        mapView.setSKTMapApiKey(MAP_API_KEY)

        binding.mapContainer.addView(mapView)
    }

    companion object {
        private val MAP_API_KEY = BuildConfig.TMAP_API_KEY
    }
}