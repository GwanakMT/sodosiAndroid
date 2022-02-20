package com.sodosi.thread

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.common.base.BaseActivity
import com.sodosi.databinding.ActivityThreadBinding

class ThreadActivity : BaseActivity<ThreadViewModel, ActivityThreadBinding>() {
    private lateinit var menuDialog: Dialog

    override fun getViewBinding() = ActivityThreadBinding.inflate(layoutInflater)

    override val viewModel: ThreadViewModel by viewModels()

    override fun observeData() {

    }

    override fun initViews() {
        setOnClickListener()
        initDialog()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnMenu.setOnClickListener { menuDialog.show() }
        binding.btnShare.setOnClickListener { }
        binding.layoutComment.btnSend.setOnClickListener {
            binding.layoutComment.etComment.apply {
                Log.v("${ThreadActivity::class.simpleName}", "$text")

                text = null

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    private fun initDialog() {
        menuDialog = Dialog(this)
        menuDialog.apply {
            setContentView(R.layout.dialog_thread_menu)

            findViewById<TextView>(R.id.tvCommentSave).setOnClickListener {
                Toast.makeText(this@ThreadActivity, "이 글 저장하기", Toast.LENGTH_SHORT).show()
            }

            findViewById<TextView>(R.id.tvCommentReport).setOnClickListener {
                menuDialog.dismiss()

                val reportDialog = ReportBottomSheetDialogFragment()
                reportDialog.show(supportFragmentManager, reportDialog.tag)
            }

            findViewById<TextView>(R.id.tvDismiss).setOnClickListener {
                menuDialog.dismiss()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }
}
