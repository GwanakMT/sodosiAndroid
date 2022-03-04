package com.sodosi.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.sodosi.R
import com.sodosi.databinding.LayoutBottomTabbarBinding

/**
 *  BottomTabView.kt
 *
 *  Created by Minji Jeong on 2022/02/13
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class BottomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs), View.OnClickListener {
    private val binding: LayoutBottomTabbarBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_bottom_tabbar, this, true)

    private var onItemClick: ((View) -> Unit)? = null

    private var currentTab: View = binding.tabHome

    init {
        binding.tabHome.setOnClickListener(this)
        binding.tabCreateMap.setOnClickListener(this)
        binding.tabMypage.setOnClickListener(this)
    }

    fun setOnItemClickListener(onItemClick: ((View) -> Unit)) {
        this.onItemClick = onItemClick
    }

    override fun onClick(v: View) {
        onItemClick?.invoke(v)
    }
}
