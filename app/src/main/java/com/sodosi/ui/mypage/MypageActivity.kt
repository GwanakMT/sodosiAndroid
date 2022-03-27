package com.sodosi.ui.mypage

import androidx.activity.viewModels
import com.sodosi.databinding.ActivityMypageBinding
import com.sodosi.ui.common.base.BaseActivity

class MypageActivity : BaseActivity<MypageViewModel, ActivityMypageBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    override fun getViewBinding() = ActivityMypageBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {

    }
}
