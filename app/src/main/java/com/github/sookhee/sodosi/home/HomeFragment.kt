package com.github.sookhee.sodosi.home

import androidx.fragment.app.viewModels
import com.github.sookhee.sodosi.BuildConfig
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentHomeBinding
import com.skt.Tmap.TMapView

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

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
