package com.sodosi.ui.sodosi

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.sodosi.databinding.FragmentSodosiBottomSheetBinding
import com.sodosi.ui.common.base.BaseFragment

/**
 *  MomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentBottomSheetFragment : BaseFragment<SodosiViewModel, FragmentSodosiBottomSheetBinding>() {

    override val viewModel: SodosiViewModel by activityViewModels()

    override fun getViewBinding() = FragmentSodosiBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        binding.tvMomentCount.text = "7"

        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun setOnClickListener() {
        binding.etMoment.setOnClickListener {
            val intent = Intent(context, CreateMomentActivity::class.java)
            startActivity(intent)
        }
    }
}
