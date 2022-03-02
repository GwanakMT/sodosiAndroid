package com.sodosi.ui.onboarding

import android.content.Intent
import androidx.fragment.app.viewModels
import com.sodosi.databinding.FragmentStep5Binding
import com.sodosi.ui.common.base.BaseFragment
import com.sodosi.ui.main.MainActivity

/**
 *  Step5Fragment.kt
 *
 *  Created by Minji Jeong on 2022/02/18
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class Step5Fragment : BaseFragment<OnboardingViewModel, FragmentStep5Binding>() {
    override fun getViewBinding() = FragmentStep5Binding.inflate(layoutInflater)

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
