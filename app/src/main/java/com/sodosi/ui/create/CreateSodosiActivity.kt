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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityCreateBinding
import com.sodosi.domain.Result
import com.sodosi.ui.common.EmojiFilter
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.sodosi.SodosiActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

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
    private var emojiFirst = true

    override val viewModel: CreateSodosiViewModel by viewModels()

    override fun getViewBinding() = ActivityCreateBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.createSodosiResult.collect { result ->
                progress.dismiss()
                when (result.first) {
                    is Result.Success -> {
                        SodosiToast.makeText(this@CreateSodosiActivity, "소도시가 정상적으로 건설됐어요!", Toast.LENGTH_SHORT).show()
                        moveToSodosiScreen((result.first as Result.Success<Long>).data)
                    }
                    is Result.Error -> {
                        SodosiToast.makeText(this@CreateSodosiActivity, "소도시 생성 실패... 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        repeatOnStarted {
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

            findViewById<TextView>(R.id.btnExit).setOnClickListener {
                exitDialog.dismiss()
                finish()
            }

            findViewById<TextView>(R.id.btnContinue).setOnClickListener {
                exitDialog.dismiss()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.CENTER)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnCreateSodosi.setOnClickListener {
            val name = binding.etSodosiName.text.toString()
            val icon = binding.tvEmoji.text.toString()
            val viewState = isSodosiPublic

            viewModel.createSodosi(name, icon, viewState)
            progress.show()
        }

        binding.ivEmoji.setOnClickListener {
            clickEmojiField()
        }

        binding.tvEmoji.setOnClickListener {
            clickEmojiField()
        }

        binding.sodosiPublic.setOnClickListener {
            isSodosiPublic = true
            binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_checked_24))
            binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_unchecked_24))
        }

        binding.sodosiPrivate.setOnClickListener {
            isSodosiPublic = false
            binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_unchecked_24))
            binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_checked_24))
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

    private fun clickEmojiField() {
        binding.etEmoji.setText(randomEmojiList[Random.nextInt(randomEmojiList.size - 1)])
        binding.etEmoji.requestFocus()
        inputMethodManager.showSoftInput(binding.etEmoji, 0)

        if (emojiFirst) {
            SodosiToast.makeText(this, "소도시 컨셉에 맞는 이모지를 골라주세요.", Toast.LENGTH_SHORT).show()
            emojiFirst = false
        }
    }

    private fun checkButtonEnable() {
        binding.btnCreateSodosi.isEnabled =
            binding.etSodosiName.text.isNotEmpty() && binding.tvEmoji.text.length == 2
    }

    private fun moveToSodosiScreen(id: Long) {
        val intent = SodosiActivity.getIntent(this, id, name = binding.etSodosiName.text.toString(), 0, viewModel.hasSodosi)

        startActivity(intent)
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreateSodosiActivity::class.java)
        }

        private val randomEmojiList = listOf(
            "\uD83D\uDE00", "\uD83D\uDE03", "\uD83D\uDE01", "\uD83D\uDE05", "\uD83E\uDD23",
            "\uD83D\uDE02", "\uD83D\uDE43", "\uD83D\uDE07", "\uD83E\uDD70", "\uD83E\uDD11",
            "\uD83E\uDD2B", "\uD83D\uDE36", "\uD83E\uDD24", "\uD83E\uDD74", "\uD83D\uDE0E",
            "\uD83D\uDE31", "\uD83D\uDE21", "\uD83D\uDE08", "\uD83D\uDC7B", "\uD83D\uDC7D",
            "\uD83D\uDC8B", "\uD83D\uDD90️", "\uD83E\uDD1F", "\uD83D\uDC85", "\uD83D\uDCAA",
            "\uD83E\uDDB6", "\uD83D\uDC42", "\uD83E\uDDE0", "\uD83E\uDDB7", "\uD83D\uDC40",
            "\uD83D\uDC76", "\uD83D\uDC81\u200D♀️", "\uD83E\uDD26\u200D♂️", "\uD83E\uDDDA",
            "\uD83C\uDFC3", "\uD83D\uDC36", "\uD83E\uDD8A", "\uD83D\uDC35", "\uD83E\uDD81",
            "\uD83D\uDC2F", "\uD83E\uDD84", "\uD83D\uDC37", "\uD83D\uDC39", "\uD83D\uDC30",
            "\uD83D\uDC3C", "\uD83D\uDC3E", "\uD83D\uDC25", "\uD83E\uDDA2", "\uD83D\uDC33",
            "\uD83D\uDC20", "\uD83D\uDC1A", "\uD83D\uDC19", "\uD83E\uDEB4", "\uD83E\uDD40",
            "\uD83C\uDF3B", "\uD83C\uDF3F", "\uD83C\uDF42", "\uD83C\uDF0D", "\uD83C\uDF1C",
            "\uD83C\uDF08", "\uD83C\uDF0A", "\uD83C\uDFD6️", "\uD83C\uDF0B", "\uD83C\uDFD4️",
            "\uD83C\uDFD8️", "\uD83C\uDFE8", "\uD83D\uDEB2", "\uD83D\uDEA7", "\uD83D\uDC9D",
            "\uD83D\uDC94", "\uD83D\uDEC0", "\uD83D\uDECC", "\uD83C\uDF80", "\uD83D\uDD2E",
        )
    }
}
