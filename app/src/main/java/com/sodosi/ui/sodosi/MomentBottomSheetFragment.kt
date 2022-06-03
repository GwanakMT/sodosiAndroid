package com.sodosi.ui.sodosi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sodosi.databinding.FragmentMomentBottomSheetBinding
import com.sodosi.ui.comment.SodosiCommentActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.sodosi.adapter.MomentListAdapter
import com.sodosi.ui.sodosi.model.PlaceModel
import kotlinx.coroutines.launch

/**
 *  MomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/05/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentBottomSheetFragment : BaseFragment<SodosiViewModel, FragmentMomentBottomSheetBinding>() {
    override val viewModel: SodosiViewModel by activityViewModels()

    private val momentAdapter by lazy { MomentListAdapter() }
    private var onDismiss: (() -> Unit)? = null

    override fun getViewBinding() = FragmentMomentBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        setMomentRecyclerView()
        setOnClickListener()
    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.momentList.collect {
                    if (it.isNotEmpty()) {
                        binding.tvMomentCount.text = "${it.size}개의 순간"
                        momentAdapter.submitList(it)
                    }
                }
            }
        }
    }

    fun setPlaceData(model: PlaceModel) {
        viewModel.getMomentList(model.placeName)
        binding.tvPlaceName.text = model.placeName
        binding.tvPlaceAddress.text = model.placeAddress
    }

    private fun setOnClickListener() {
        binding.btnDismiss.setOnClickListener {
            onDismiss?.invoke()
        }
    }

    private fun setMomentRecyclerView() {
        binding.rvMoment.adapter = momentAdapter
        momentAdapter.onItemClick = {
            val intent = Intent(requireContext(), SodosiCommentActivity::class.java)
            startActivity(intent)
        }

        momentAdapter.onPhotoClick = { imageUrlList, position ->

        }
    }

    companion object {
        fun newInstance(
            onDismiss: () -> Unit
        ): MomentBottomSheetFragment {
            val newFragment = MomentBottomSheetFragment()
            newFragment.arguments = Bundle()
            newFragment.onDismiss = onDismiss

            return newFragment
        }
    }
}
