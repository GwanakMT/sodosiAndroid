package com.github.sookhee.sodosi.onboarding

import android.content.Intent
import androidx.fragment.app.viewModels
import com.github.sookhee.sodosi.common.base.BaseFragment
import com.github.sookhee.sodosi.databinding.FragmentStep6Binding
import com.github.sookhee.sodosi.main.MainActivity

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
