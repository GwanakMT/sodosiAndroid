package com.sodosi.ui.common.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.sodosi.R
import com.sodosi.ui.common.extensions.dp

/**
 *  SodosiButton.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@SuppressLint("ResourceAsColor")
class SodosiButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    init {
        text = "시작하기"
        textSize = 16.toFloat()
        typeface = ResourcesCompat.getFont(context, R.font.pretendard_bold)
        setTextColor(ContextCompat.getColor(context, R.color.white))
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(context, R.drawable.rounded_button_green)
        setPadding(16.dp)
    }

    fun setStateNormal() {
        alpha = 1.0F
        isClickable = true
    }

    fun setStateDisable() {
        alpha = 0.3F
        isClickable = false
    }
}
