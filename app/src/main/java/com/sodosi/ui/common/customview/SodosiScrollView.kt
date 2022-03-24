package com.sodosi.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 *  SodosiScrollView.kt
 *
 *  Created by Minji Jeong on 2022/03/24
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SodosiScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): NestedScrollView(context, attrs, defStyleAttr) {
    var xDistance = 0f
    var yDistance = 0f
    var lastX = 0f
    var lastY = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                run {
                    yDistance = 0f
                    xDistance = yDistance
                }
                lastX = ev.x
                lastX = ev.y

                onTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val curY = ev.y
                xDistance += kotlin.math.abs(curX - lastX)
                yDistance += kotlin.math.abs(curY - lastY)
                lastX = curX
                lastY = curY
                if(xDistance > yDistance) return false
            }
        }

        return super.onInterceptTouchEvent(ev)
    }
}
