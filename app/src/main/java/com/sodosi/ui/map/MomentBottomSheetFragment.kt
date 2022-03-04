package com.sodosi.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sodosi.databinding.FragmentMapBottomSheetBinding

/**
 *  MomentBottomSheetFragment.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class MomentBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMapBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from<View>(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        BottomSheetBehavior.from(bottomSheet ?: return).apply {
            isHideable = false
            peekHeight = 400
        }

        setOnClickListener()
    }

    private fun setOnClickListener() {

    }
}
