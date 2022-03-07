package com.sodosi.ui.create

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
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
    private lateinit var exitDialog: Dialog

    override val viewModel: CreateViewModel by viewModels()

    override fun getViewBinding() = ActivityCreateBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    override fun initViews() = with(binding) {
        initAppbar()
        initDialog()
        setOnClickListener()
    }

    override fun onBackPressed() {
        exitDialog.show()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.create_title))
        }
    }

    private fun initDialog() {
        exitDialog = Dialog(this).apply {
            setContentView(R.layout.dialog_create_exit)

            findViewById<Button>(R.id.btnExit).setOnClickListener {
                exitDialog.dismiss()
                finish()
            }

            findViewById<Button>(R.id.btnContinue).setOnClickListener {
                exitDialog.dismiss()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
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
