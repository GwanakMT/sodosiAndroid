package com.sodosi.ui.sodosi.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.sodosi.R
import com.sodosi.databinding.FragmentMomentBottomSheetBinding
import com.sodosi.ui.comment.SodosiCommentActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.sodosi.SodosiViewModel
import com.sodosi.ui.sodosi.adapter.MomentListAdapter
import com.sodosi.ui.sodosi.model.MomentModel

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

    }

    fun setPlaceData(model: MomentModel) {
        val momentList = viewModel.getMomentList(model.addressDetail)
        if (!momentList.isNullOrEmpty()) {
            binding.tvPlaceName.text = model.addressDetail
            binding.tvPlaceAddress.text = model.roadAddress
            binding.tvMomentCount.text = getString(R.string.sodosi_moment_count, momentList.size)
            momentAdapter.submitList(momentList)
        }
    }

    private fun setOnClickListener() {
        binding.btnDismiss.setOnClickListener {
            onDismiss?.invoke()
        }
    }

    private fun setMomentRecyclerView() {
        binding.rvMoment.adapter = momentAdapter
        momentAdapter.onItemClick = {
            val intent = SodosiCommentActivity.getIntent(requireContext(), it)
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
