package com.sodosi.mypage

import androidx.fragment.app.viewModels
import com.sodosi.common.base.BaseFragment
import com.sodosi.databinding.FragmentMypageBinding

/**
 *  MypageFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MypageFragment : BaseFragment<MypageViewModel, FragmentMypageBinding>() {
    override fun getViewBinding() = FragmentMypageBinding.inflate(layoutInflater)

    override val viewModel: MypageViewModel by viewModels()

    override fun initViews() = with(binding) {

    }

    override fun observeData() {

    }
}
