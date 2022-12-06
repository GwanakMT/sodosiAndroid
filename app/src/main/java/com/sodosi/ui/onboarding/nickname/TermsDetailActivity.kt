package com.sodosi.ui.onboarding.nickname

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityTermsDetailBinding
import com.sodosi.domain.entity.Terms
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.onboarding.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  TermsDetailActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/14
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class TermsDetailActivity : BaseActivity<OnboardingViewModel, ActivityTermsDetailBinding>() {
    override fun getViewBinding() = ActivityTermsDetailBinding.inflate(layoutInflater)

    override val viewModel: OnboardingViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        val title = intent.getStringExtra(TERMS_TITLE) ?: ""
        initAppbar(title)

        val contents = intent.getStringExtra(TERMS_CONTENTS) ?: ""
        binding.tvTermsContents.text = contents
    }

    private fun initAppbar(title: String) {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(title)
        }
    }

    companion object {
        private const val TERMS_TITLE = "TERMS_TITLE"
        private const val TERMS_CONTENTS = "TERMS_CONTENTS"

        fun getIntent(context: Context, terms: Terms): Intent {
            return Intent(context, TermsDetailActivity::class.java).apply {
                putExtra(TERMS_TITLE, terms.title)
                putExtra(TERMS_CONTENTS, terms.content)
            }
        }
    }
}
