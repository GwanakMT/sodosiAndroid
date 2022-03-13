package com.sodosi.ui.common.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.sodosi.R
import com.sodosi.databinding.LayoutPasswordBinding

class SodosiPasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs), View.OnClickListener {
    private val binding: LayoutPasswordBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_password, this, true)
    private var isPasswordShow = false

    lateinit var textChangedListener: (Editable?) -> Unit

    init {
        setOnClickListener()

        binding.etPassword.addTextChangedListener {
            textChangedListener(it)
            if ("$it".length == 0) {
                binding.etPassword.typeface =
                    ResourcesCompat.getFont(context, R.font.pretendard_regular)
            } else {
                binding.etPassword.typeface =
                    ResourcesCompat.getFont(context, R.font.pretendard_semibold)
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivPassword.setOnClickListener {
            if (isPasswordShow) {
                binding.ivPassword.setImageResource(R.drawable.ic_edit_hide)
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.ivPassword.setImageResource(R.drawable.ic_edit_show)
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            isPasswordShow = !isPasswordShow
            binding.etPassword.setSelection(binding.etPassword.text.toString().length)
        }
    }

    fun setViewWarning() {
        binding.inputBackground.background = ContextCompat.getDrawable(context, R.drawable.background_rounded_pink)
        binding.tvWarning.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {

    }
}
