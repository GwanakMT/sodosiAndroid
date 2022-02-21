package com.sodosi.ui.location

import androidx.fragment.app.viewModels
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentSearchLocationBinding

/**
 *  SearchLocationFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class SearchLocationFragment : BaseFragment<LocationViewModel, FragmentSearchLocationBinding>() {
    override val viewModel: LocationViewModel by viewModels()

    override fun getViewBinding(): FragmentSearchLocationBinding = FragmentSearchLocationBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
