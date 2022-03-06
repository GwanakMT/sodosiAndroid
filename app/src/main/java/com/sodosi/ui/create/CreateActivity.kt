package com.sodosi.ui.create

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivityCreateBinding
import com.sodosi.ui.common.base.BaseActivity

/**
 *  CreateActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CreateActivity : BaseActivity<CreateViewModel, ActivityCreateBinding>() {
    override val viewModel: CreateViewModel by viewModels()

    override fun getViewBinding() = ActivityCreateBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    override fun initViews() = with(binding) {
        initAppbar()
        setOnClickListener()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "정말 나가시겠어요?", Toast.LENGTH_SHORT).show()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.create_title))
        }
    }

    private fun setOnClickListener() {
        binding.btnCreateSodosi.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}
