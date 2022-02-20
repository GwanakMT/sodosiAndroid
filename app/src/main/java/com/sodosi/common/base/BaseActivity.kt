package com.sodosi.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

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
}
