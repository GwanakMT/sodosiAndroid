package com.sodosi.ui.sodosi.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.sodosi.databinding.FragmentCreateMomentBottomSheetBinding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.post.SearchPlaceActivity
import com.sodosi.ui.sodosi.SodosiViewModel

/**
 *  CreateMomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/06/28
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CreateMomentBottomSheetFragment : BaseFragment<SodosiViewModel, FragmentCreateMomentBottomSheetBinding>() {
    override val viewModel: SodosiViewModel by activityViewModels()

    override fun getViewBinding() = FragmentCreateMomentBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        initView()
        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun initView() {
        val startPlace = arguments?.getString(KEY_START_PLACE, "") ?: ""
        setCurrentPlaceName(startPlace)
    }

    private fun setOnClickListener() {
        binding.currentPlace.setOnClickListener {
            startActivity(Intent(context, SearchPlaceActivity::class.java))
        }
    }

    fun setCurrentPlaceName(placeName: String) {
        binding.currentPlace.text = placeName
    }

    companion object {
        private const val KEY_START_PLACE = "KEY_START_PLACE"

        fun newInstance(startPlace: String): CreateMomentBottomSheetFragment {
            val newFragment = CreateMomentBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_START_PLACE, startPlace)
                }
            }

            return newFragment
        }
    }
}
