package com.sodosi.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 *  PermissionManager.kt
 *
 *  Created by Minji Jeong on 2022/02/22
 *  Copyright © 2022 GwanakMT All rights reserved.
 *
 */

object PermissionManager {
    const val REQUEST_CODE = 1000
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

    fun hasPermission(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun getPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE)
    }
}