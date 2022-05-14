package com.sodosi.ui.common.customview

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sodosi.R
import com.sodosi.databinding.ToastSodosiBinding
import com.sodosi.ui.common.extensions.dp

/**
 *  SodosiToast.kt
 *
 *  Created by Minji Jeong on 2022/05/14
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

object SodosiToast {
    fun makeText(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastSodosiBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_sodosi, null, false)

        binding.message.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.dp)
            this.duration = duration
            view = binding.root
        }
    }
}