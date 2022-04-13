package com.sodosi.ui.map

import androidx.fragment.app.activityViewModels
import com.sodosi.databinding.FragmentMapBottomSheetBinding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  MomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentBottomSheetFragment : BaseFragment<MapViewModel, FragmentMapBottomSheetBinding>() {

    override val viewModel: MapViewModel by activityViewModels()

    override fun getViewBinding() = FragmentMapBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        binding.tvMomentCount.text = "7"
    }

    override fun observeData() {

    }
}
