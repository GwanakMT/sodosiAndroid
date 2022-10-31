package com.sodosi.ui.create

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityEditSodosiBinding
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.EmojiFilter
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditSodosiActivity : BaseActivity<EditSodosiViewModel, ActivityEditSodosiBinding>() {
    private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private lateinit var sodosiInfo: SodosiModel
    private var isSodosiPublic = true

    override val viewModel: EditSodosiViewModel by viewModels()

    override fun getViewBinding() = ActivityEditSodosiBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.patchSodosiResult.collect { isSuccess ->
                progress.dismiss()

                if (isSuccess) {
                    SodosiToast.makeText(this@EditSodosiActivity, "소도시가 정상적으로 수정되었어요!", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.putExtra(KEY_SODOSI_MODEL, sodosiInfo)
                    setResult(RESULT_OK, intent)

                    finish()

                } else {
                    SodosiToast.makeText(this@EditSodosiActivity, "소도시 수정 실패... 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        sodosiInfo = intent.getParcelableExtra(KEY_SODOSI_MODEL) as? SodosiModel ?: return@with

        initAppbar()

        binding.etSodosiName.setText(sodosiInfo.name)
        binding.tvEmoji.text = sodosiInfo.icon
        when(sodosiInfo.viewStatus) {
            true -> setSodosiPublic()
            false -> setSodosiPrivate()
        }

        checkButtonEnable()

        setOnClickListener()
        setTextChangeListener()

        return@with
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.edit_title))
        }
    }

    private fun setOnClickListener() {
        binding.btnCreateSodosi.setOnClickListener {
            val name = binding.etSodosiName.text.toString()
            val icon = binding.tvEmoji.text.toString()
            val viewState = isSodosiPublic

            sodosiInfo = sodosiInfo.copy(name = name, icon = icon, viewStatus = viewState)

            progress.show()
            viewModel.patchSodosi(sodosiInfo.id, name, icon, viewState)
        }

        binding.ivEmoji.setOnClickListener {
            clickEmojiField()
        }

        binding.tvEmoji.setOnClickListener {
            clickEmojiField()
        }

        binding.sodosiPublic.setOnClickListener {
            setSodosiPublic()
        }

        binding.sodosiPrivate.setOnClickListener {
            setSodosiPrivate()
        }
    }

    private fun setSodosiPublic() {
        isSodosiPublic = true
        binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_checked_24))
        binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_unchecked_24))
    }

    private fun setSodosiPrivate() {
        isSodosiPublic = false
        binding.ivPublic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_unchecked_24))
        binding.ivPrivate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_interface_checked_24))
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
        binding.etEmoji.requestFocus()
        inputMethodManager.showSoftInput(binding.etEmoji, 0)
    }

    private fun checkButtonEnable() {
        binding.btnCreateSodosi.isEnabled =
            binding.etSodosiName.text.isNotEmpty() && binding.tvEmoji.text.length == 2
    }

    companion object {
        const val KEY_SODOSI_MODEL = "KEY_SODOSI_MODEL"

        fun getIntent(context: Context, sodosi: SodosiModel): Intent {
            return Intent(context ,EditSodosiActivity::class.java).apply {
                putExtra(KEY_SODOSI_MODEL, sodosi)
            }
        }
    }
}
