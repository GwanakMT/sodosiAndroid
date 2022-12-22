package com.sodosi.ui.common.customview

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.sodosi.R
import com.sodosi.databinding.LayoutClearButtonPasswordBinding

class SodosiClearButtonPasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs), View.OnClickListener {
    private val binding: LayoutClearButtonPasswordBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_clear_button_password, this, true)

    lateinit var textChangedListener: (Editable?) -> Unit

    init {
        setOnClickListener()

        binding.etPassword.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                binding.ivClearText.visibility = View.VISIBLE
            } else {
                binding.ivClearText.visibility = View.GONE
            }

            textChangedListener(it)
        }

        binding.etPassword.setOnFocusChangeListener { view, isFocus ->
            if (!isFocus) {
                binding.ivClearText.visibility = View.GONE
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivClearText.setOnClickListener {
            binding.etPassword.text = null
        }
    }

    fun getText(): String = binding.etPassword.text.toString()

    fun setViewWarning(text: String? = null) {
        binding.inputBackground.background =
            ContextCompat.getDrawable(context, R.drawable.background_rounded_pink)
        binding.tvWarning.visibility = View.VISIBLE
        text?.let { binding.tvWarning.text = text }
    }

    fun disableWarning() {
        binding.inputBackground.background =
            ContextCompat.getDrawable(context, R.drawable.background_rounded_gray)
        binding.tvWarning.visibility = View.GONE
    }

    fun setHint(hint: String) {
        binding.etPassword.hint = hint
    }

    fun setFocus() {
        binding.etPassword.requestFocus()
    }

    override fun onClick(view: View?) {

    }
}
