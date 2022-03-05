package com.sodosi.ui.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.sodosi.R

/**
 *  BaseActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initState()
    }

    open fun initState() {
        initViews()
        observeData()
    }

    open fun initViews() = Unit

    abstract fun observeData()

    fun changeStatusBarColorBlack() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.sub_black)
        WindowInsetsControllerCompat(window, binding.root).isAppearanceLightStatusBars = false
    }

    fun changeStatusBarColorWhite() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowInsetsControllerCompat(window, binding.root).isAppearanceLightStatusBars = true

    }
}
