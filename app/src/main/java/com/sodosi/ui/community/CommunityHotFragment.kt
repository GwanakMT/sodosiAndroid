package com.sodosi.ui.community

import androidx.fragment.app.viewModels
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentCommunityHotBinding

/**
 *  CommunityHotFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class CommunityHotFragment : BaseFragment<CommunityViewModel, FragmentCommunityHotBinding>() {
    override fun getViewBinding() = FragmentCommunityHotBinding.inflate(layoutInflater)

    override val viewModel: CommunityViewModel by viewModels()

    override fun initViews(): Unit = with(binding) {

    }

    override fun observeData() {

    }
}
