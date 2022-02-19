package com.github.sookhee.sodosi.thread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sookhee.sodosi.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 *  ReportBottomSheetDialogFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/20
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ReportBottomSheetDialogFragment() : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from<View>(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}
