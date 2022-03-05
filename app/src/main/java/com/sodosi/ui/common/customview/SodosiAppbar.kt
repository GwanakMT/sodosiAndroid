package com.sodosi.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.sodosi.R
import com.sodosi.databinding.LayoutAppbarBinding

/**
 *  SodosiAppbar.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiAppbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private val binding: LayoutAppbarBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_appbar, this, true)

    fun initLeftButton(resId: Int, onClickEvent: () -> Unit) {
        binding.btnLeft.apply {
            setImageResource(resId)
            visibility = View.VISIBLE
            setOnClickListener {
                onClickEvent()
            }
        }
    }

    fun initAppbarTitle(title: String) {
        binding.tvAppbarTitle.apply {
            visibility = View.VISIBLE
            text = title
        }
    }

    fun initRightButton(resId: Int, onClickEvent: () -> Unit) {
        binding.btnRight.apply {
            setImageResource(resId)
            visibility = View.VISIBLE
            setOnClickListener {
                onClickEvent()
            }
        }
    }
}
