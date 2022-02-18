package com.github.sookhee.sodosi.location

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentMyLocationBinding

/**
 *  MyLocationFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class MyLocationFragment : BaseFragment<LocationViewModel, FragmentMyLocationBinding>() {
    override val viewModel: LocationViewModel by viewModels()

    override fun getViewBinding(): FragmentMyLocationBinding = FragmentMyLocationBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnAddLocation.setOnClickListener {
            findNavController().navigate(MyLocationFragmentDirections.actionFragmentMyLocationToFragmentSearchLoacation())
        }
    }
}
