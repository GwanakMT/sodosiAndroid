package com.sodosi.ui.comment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiCommentBinding
import com.sodosi.databinding.LayoutSodosiCommentMenuDialogBinding
import com.sodosi.databinding.LayoutSodosiReportDialogBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast

class SodosiCommentActivity : BaseActivity<SodosiCommentViewModel, ActivitySodosiCommentBinding>() {
    private lateinit var menuDialog: Dialog
    private lateinit var reportDialog: Dialog

    override val viewModel: SodosiCommentViewModel by viewModels()

    override fun getViewBinding() = ActivitySodosiCommentBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        initAppbar()
        initMenuDialog()
        initReportDialog()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initRightButton(R.drawable.ic_menu_more_vertical) {
                menuDialog.show()
            }

            initAppbarTitle(getString(R.string.comment))
        }
    }

    private fun initMenuDialog() {
        menuDialog = Dialog(this)
        menuDialog.apply {
            val binding = LayoutSodosiCommentMenuDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding) {
                tvReport.setOnClickListener {
                    menuDialog.dismiss()
                    reportDialog.show()
                }

                tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }

    private fun initReportDialog() {
        reportDialog = Dialog(this)
        reportDialog.apply {
            val binding = LayoutSodosiReportDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.onItemClick = {
                SodosiToast.makeText(context, getString(R.string.report_submit), Toast.LENGTH_SHORT).show()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }
}
