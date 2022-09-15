package com.sodosi.ui.common.customview

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.sodosi.R

/**
 *  PermissionDialog.kt
 *
 *  Created by Minji Jeong on 2022/09/16
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class PermissionDialog(context: Context): Dialog(context) {
    init {
        setContentView(R.layout.dialog_permission)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        findViewById<AppCompatButton>(R.id.btnMoveToSetting).setOnClickListener {
            moveToSetting()
            dismiss()
        }

        findViewById<AppCompatButton>(R.id.btnProgressKill).setOnClickListener {
            System.exit(0)
        }
    }

    private fun moveToSetting() {
        val appIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${context.packageName}"))
        appIntent.addCategory(Intent.CATEGORY_DEFAULT)
        appIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(appIntent)
    }
}