package com.github.sookhee.sodosi.thread

import androidx.activity.viewModels
import com.github.sookhee.sodosi.common.base.BaseActivity
import com.github.sookhee.sodosi.databinding.ActivityThreadBinding

class ThreadActivity : BaseActivity<ThreadViewModel, ActivityThreadBinding>() {
    override fun getViewBinding() = ActivityThreadBinding.inflate(layoutInflater)

    override val viewModel: ThreadViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnMenu.setOnClickListener {  }
        binding.btnShare.setOnClickListener {  }
    }
}
