package com.sodosi.ui.onboarding

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import com.sodosi.R
import com.sodosi.util.PermissionManager
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.databinding.FragmentStep6Binding
import com.sodosi.ui.main.MainActivity

/**
 *  Step6Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step6Fragment : BaseFragment<OnboardingViewModel, FragmentStep6Binding>() {
    override fun getViewBinding() = FragmentStep6Binding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                activity?.onBackPressed()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }
    }
}
