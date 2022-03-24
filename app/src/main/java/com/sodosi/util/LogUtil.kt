package com.sodosi.util

import android.util.Log
import com.sodosi.BuildConfig

/**
 *  LogUtil.kt
 *
 *  Created by Minji Jeong on 2022/03/24
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

object LogUtil {
    private const val TAG = "SODOSI"

    fun v(msg: String, className: String = "") {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "[$className] $msg")
        }
    }

    fun d(msg: String, className: String = "") {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "[$className] $msg")
        }
    }

    fun w(msg: String, className: String = "") {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "[$className] $msg")
        }
    }

    fun e(msg: String, className: String = "") {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "[$className] $msg")
        }
    }

    fun i(msg: String, className: String = "") {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "[$className] $msg")
        }
    }
}