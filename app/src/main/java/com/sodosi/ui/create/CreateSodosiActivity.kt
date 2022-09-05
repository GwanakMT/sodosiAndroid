package com.sodosi.ui.create

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
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityCreateBinding
import com.sodosi.domain.Result
import com.sodosi.ui.common.EmojiFilter
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.sodosi.SodosiActivity
import com.sodosi.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 *  CreateSodosiActivity.kt
 *
 *  Created by Minji Jeong on 2022/03/07
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

@AndroidEntryPoint
class CreateSodosiActivity : BaseActivity<CreateSodosiViewModel, ActivityCreateBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private lateinit var exitDialog: Dialog
    private var isSodosiPublic = true

    override val viewModel: CreateSodosiViewModel by viewModels()

    override fun getViewBinding() = ActivityCreateBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.createSodosi.collect {
                when (it) {
                    is Result.Success -> {
                        moveToSodosiScreen()
                    }
                    is Result.Error -> {

                    }
                }
            }

            viewModel.isLoading.collect {
                when (it) {
                    true -> {} // show
                    false -> {} // hide
                }
            }
        }
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

    private fun setOnClickListener() {
        binding.btnCreateSodosi.setOnClickListener {
            val name = binding.etSodosiName.text.toString()
            val icon = binding.tvEmoji.text.toString()
            val viewState = isSodosiPublic

            LogUtil.d("name: $name, icon: $icon, viewState: $viewState")

            viewModel.createSodosi(name, icon, viewState)
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
            binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_circle_checked))
            binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_circle_unchecked))
        }

        binding.sodosiPrivate.setOnClickListener {
            isSodosiPublic = false
            binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_circle_unchecked))
            binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_circle_checked))
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

    private fun moveToSodosiScreen() {
        val intent = Intent(this, SodosiActivity::class.java)
        intent.putExtra(EXTRA_SODOSI_NAME, binding.etSodosiName.text.toString())
        intent.putExtra(EXTRA_SODOSI_EMOJI, binding.tvEmoji.text)
        intent.putExtra(EXTRA_SODOSI_IS_PUBLIC, isSodosiPublic)

        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_SODOSI_NAME = "SODOSI_NAME"
        const val EXTRA_SODOSI_EMOJI = "SODOSI_EMOJI"
        const val EXTRA_SODOSI_IS_PUBLIC = "SODOSI_IS_PUBLIC"
    }
}
