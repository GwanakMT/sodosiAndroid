package com.sodosi.ui.common.extensions

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 *  ViewExtension.kt
 *
 *  Created by Minji Jeong on 2022/03/05
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Fragment.navigate(from: Int, navigate: ()->Unit) {
    if (findNavController().currentDestination?.id == from) {
        navigate.invoke()
    }
}
