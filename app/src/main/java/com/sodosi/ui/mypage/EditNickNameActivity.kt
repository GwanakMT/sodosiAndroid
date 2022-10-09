package com.sodosi.ui.mypage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityEditNickNameBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNickNameActivity : BaseActivity<MypageViewModel, ActivityEditNickNameBinding>() {
    override val viewModel: MypageViewModel by viewModels()

    override fun getViewBinding() = ActivityEditNickNameBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.nickNameChanged.collect { isChanged ->
                if (isChanged) {
                    SodosiToast.makeText(this@EditNickNameActivity, "닉네임 변경 완료!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    SodosiToast.makeText(this@EditNickNameActivity, "닉네임 변경에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initViews() {
        val nickName = intent.getStringExtra(NICKNAME) ?: ""
        binding.etNickname.setText(nickName)

        initAppbar()
        setListener()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.mypage_edit_nickname))
        }
    }

    private fun setListener() {
        binding.btnClearText.setOnClickListener {
            binding.etNickname.text = null
        }

        binding.btnFinish.setOnClickListener {
            val newNickName = binding.etNickname.text.toString()
            if (newNickName.isNotEmpty()) {
                viewModel.changeNickName(newNickName)
            }
        }

        binding.etNickname.addTextChangedListener {
            if ("$it".length > 0) {
                binding.btnFinish.setTextColor(ContextCompat.getColor(this, R.color.green600))
            } else {
                binding.btnFinish.setTextColor(Color.parseColor("#ADADAD"))
            }
        }
    }

    companion object {
        private const val NICKNAME = "NICKNAME"
        fun getIntent(context: Context, nickName: String): Intent {
            return Intent(context, EditNickNameActivity::class.java).apply {
                putExtra(NICKNAME, nickName)
            }
        }
    }
}
