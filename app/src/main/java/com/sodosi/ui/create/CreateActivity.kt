package com.sodosi.ui.create

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityCreateBinding
import com.sodosi.ui.common.EmojiFilter
import com.sodosi.ui.common.base.BaseActivity

/**
 *  CreateActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class CreateActivity : BaseActivity<CreateViewModel, ActivityCreateBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private lateinit var exitDialog: Dialog
    private var isSodosiPublic = true

    override val viewModel: CreateViewModel by viewModels()

    override fun getViewBinding() = ActivityCreateBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    override fun initViews() = with(binding) {
        initAppbar()
        initDialog()
        setOnClickListener()

        setTextChangeListener()

        return@with
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

            findViewById<CardView>(R.id.btnExit).setOnClickListener {
                exitDialog.dismiss()
                finish()
            }

            findViewById<CardView>(R.id.btnContinue).setOnClickListener {
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setOnClickListener() {
        binding.btnCreateSodosi.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra(EXTRA_SODOSI_NAME, binding.etSodosiName.text.toString())
            intent.putExtra(EXTRA_SODOSI_EMOJI, binding.tvEmoji.text)
            intent.putExtra(EXTRA_SODOSI_IS_PUBLIC, isSodosiPublic)

            startActivity(intent)
            finish()
        }

        binding.ivEmoji.setOnClickListener {
            binding.etEmoji.text = null
            binding.etEmoji.requestFocus()
            inputMethodManager.showSoftInput(binding.etEmoji, 0)
        }

        binding.tvEmoji.setOnClickListener {
            binding.etEmoji.text = null
            binding.etEmoji.requestFocus()
            inputMethodManager.showSoftInput(binding.etEmoji, 0)
        }

        binding.sodosiPublic.setOnClickListener {
            isSodosiPublic = true
            binding.ivPublic.setImageDrawable(getDrawable(R.drawable.ic_interface_circle_checked))
            binding.ivPrivate.setImageDrawable(getDrawable(R.drawable.ic_interface_circle_unchecked))
        }

        binding.sodosiPrivate.setOnClickListener {
            isSodosiPublic = false
            binding.ivPublic.setImageDrawable(getDrawable(R.drawable.ic_interface_circle_unchecked))
            binding.ivPrivate.setImageDrawable(getDrawable(R.drawable.ic_interface_circle_checked))
        }
    }

    private fun setTextChangeListener() {
        binding.etSodosiName.addTextChangedListener {
            checkButtonEnable()
        }

        binding.etEmoji.filters = arrayOf(EmojiFilter())
        binding.etEmoji.addTextChangedListener {
            try {
                if (it?.isNotEmpty() == true) {
                    binding.ivEmoji.visibility = View.GONE
                    checkButtonEnable()
                }

                val emoji = it?.substring(it.length - 2)
                binding.tvEmoji.text = emoji
                binding.etEmoji.text = emoji as? Editable
            } catch (e: Exception) {

            }
        }
    }

    private fun checkButtonEnable() {
        binding.btnCreateSodosi.isEnabled =
            binding.etSodosiName.text.isNotEmpty() && binding.tvEmoji.text.length == 2
    }

    companion object {
        const val EXTRA_SODOSI_NAME = "SODOSI_NAME"
        const val EXTRA_SODOSI_EMOJI = "SODOSI_EMOJI"
        const val EXTRA_SODOSI_IS_PUBLIC = "SODOSI_IS_PUBLIC"
    }
}
