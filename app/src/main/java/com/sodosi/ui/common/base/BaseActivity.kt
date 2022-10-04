package com.sodosi.ui.common.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.sodosi.R
import com.sodosi.ui.common.customview.PermissionDialog
import com.sodosi.ui.common.customview.Progress
import com.sodosi.ui.onboarding.OnboardingActivity
import com.sodosi.ui.onboarding.nickname.TermsDetailActivity

/**
 *  BaseActivity.kt
 *
 *  Created by Minji Jeong on 2022/02/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    val progress: Progress by lazy { Progress(this) }
    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            PermissionDialog(this).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initState()
    }

    override fun onResume() {
        super.onResume()
        if (this !is OnboardingActivity && this !is TermsDetailActivity) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    PermissionDialog(this).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
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

    fun clearAndMoveToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

    fun clearAndMoveToMain() {

    }
}
