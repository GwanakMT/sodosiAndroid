package com.github.sookhee.sodosi.thread

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sookhee.sodosi.R
import com.github.sookhee.sodosi.databinding.LayoutBottomSheetReportBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 *  ReportBottomSheetDialogFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/20
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ReportBottomSheetDialogFragment() : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutBottomSheetReportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutBottomSheetReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from<View>(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.onItemClick = {
            Log.v("${ReportBottomSheetDialogFragment::class.simpleName}", "$it")

            binding.layoutChooseReport.visibility = View.GONE
            binding.layoutGreetingReport.visibility = View.VISIBLE
        }
    }
}
