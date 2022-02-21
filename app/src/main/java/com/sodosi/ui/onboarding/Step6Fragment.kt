package com.sodosi.ui.onboarding

import android.content.Intent
import androidx.fragment.app.viewModels
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
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

            activity?.finish()
        }
    }

    override fun observeData() {

    }
}
