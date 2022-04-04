package com.sodosi.ui.create

import androidx.activity.viewModels
import com.sodosi.databinding.ActivityLoadingBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  LoadingActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class LoadingActivity : BaseActivity<CreateViewModel, ActivityLoadingBinding>() {
    override val viewModel: CreateViewModel by viewModels()

    override fun getViewBinding() = ActivityLoadingBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    override fun initViews() = with(binding) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)

            finish()
        }

        LogUtil.d("${CreateActivity.EXTRA_SODOSI_NAME} : ${intent.extras?.get(CreateActivity.EXTRA_SODOSI_NAME)}")
        LogUtil.d("${CreateActivity.EXTRA_SODOSI_EMOJI} : ${intent.extras?.get(CreateActivity.EXTRA_SODOSI_EMOJI)}")
        LogUtil.d("${CreateActivity.EXTRA_SODOSI_IS_PUBLIC} : ${intent.extras?.get(CreateActivity.EXTRA_SODOSI_IS_PUBLIC)}")

        return@with
    }

    override fun onBackPressed() {

    }
}
