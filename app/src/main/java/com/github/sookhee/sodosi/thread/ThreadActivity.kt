package com.github.sookhee.sodosi.thread

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.github.sookhee.sodosi.R
import com.github.sookhee.sodosi.common.base.BaseActivity
import com.github.sookhee.sodosi.databinding.ActivityThreadBinding

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
