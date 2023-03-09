package com.sodosi.ui.sodosi.bottomsheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.sodosi.R
import com.sodosi.databinding.FragmentMomentBottomSheetBinding
import com.sodosi.databinding.LayoutMomentReportDialogBinding
import com.sodosi.databinding.LayoutSodosiCommentMenuDialogBinding
import com.sodosi.ui.comment.SodosiCommentActivity
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.common.customview.HorizontalItemDecoration
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.post.ZoomPhotoActivity
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

    private lateinit var menuDialog: Dialog
    private lateinit var reportDialog: Dialog

    override fun getViewBinding() = FragmentMomentBottomSheetBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        setMomentRecyclerView()
        setOnClickListener()

        initMenuDialog()
        initReportDialog()
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
            val intent = ZoomPhotoActivity.getIntent(requireContext(), position, imageUrlList.map { it.toUri() })
            startActivity(intent)
        }

        momentAdapter.onMenuClick = {
            menuDialog.show()
        }

        val dividerItemDecoration = HorizontalItemDecoration(
            ContextCompat.getDrawable(requireContext(), R.drawable.horizontal_decoration) ?: return
        )

        binding.rvMoment.addItemDecoration(dividerItemDecoration)
    }

    private fun initMenuDialog() {
        menuDialog = Dialog(requireContext())
        menuDialog.apply {
            val binding = LayoutSodosiCommentMenuDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding) {
                tvReport.setOnClickListener {
                    menuDialog.dismiss()
                    reportDialog.show()
                }

                tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }

    private fun initReportDialog() {
        reportDialog = Dialog(requireContext())
        reportDialog.apply {
            val binding = LayoutMomentReportDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.onItemClick = {
                reportDialog.dismiss()
                SodosiToast.makeText(requireContext(), getString(R.string.report_submit), Toast.LENGTH_SHORT).show()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
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
