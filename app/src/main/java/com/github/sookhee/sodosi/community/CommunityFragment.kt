package com.github.sookhee.sodosi.community

import androidx.fragment.app.viewModels
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentCommunityBinding

/**
 *  CommunityFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CommunityFragment : BaseFragment<CommunityViewModel, FragmentCommunityBinding>() {
    override fun getViewBinding() = FragmentCommunityBinding.inflate(layoutInflater)

    override val viewModel: CommunityViewModel by viewModels()

    override fun initViews() = with(binding) {

    }

    override fun observeData() {

    }
}
