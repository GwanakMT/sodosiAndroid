package com.sodosi.ui.comment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiCommentBinding
import com.sodosi.databinding.LayoutMomentReportDialogBinding
import com.sodosi.databinding.LayoutSodosiCommentMenuDialogBinding
import com.sodosi.domain.Result
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.dp
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.common.extensions.setImageWithUrl
import com.sodosi.ui.common.extensions.setVisible
import com.sodosi.ui.sodosi.model.MomentModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SodosiCommentActivity : BaseActivity<SodosiCommentViewModel, ActivitySodosiCommentBinding>() {
    private lateinit var menuDialog: Dialog
    private lateinit var reportDialog: Dialog
    private lateinit var momentInfo: MomentModel

    override val viewModel: SodosiCommentViewModel by viewModels()

    override fun getViewBinding() = ActivitySodosiCommentBinding.inflate(layoutInflater)

    override fun observeData() {
        repeatOnStarted {
            viewModel.reportResult.collect {
                progress.dismiss()
                reportDialog.dismiss()
                SodosiToast.makeText(this@SodosiCommentActivity, getString(R.string.report_submit), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initViews() = with(binding) {
        momentInfo = intent.getParcelableExtra<MomentModel>(KEY_MOMENT) as MomentModel

        initAppbar()
        initMenuDialog()
        initReportDialog()
        initMomentLayout()

        return@with
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
            val binding = LayoutMomentReportDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.onItemClick = {
                progress.show()
                viewModel.reportMoment(momentInfo.sodosiId, momentInfo.id, it)
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }

    private fun initMomentLayout() {
        val padding = 4.dp
        binding.momentLayout.apply {
            item = momentInfo
            tvPlaceName.setGone()
            tvComment.maxLines = Int.MAX_VALUE

            when (momentInfo.photo.size) {
                0 -> {
                    photoLayout.setGone()
                }

                1 -> {
                    secondLayout.setGone()
                    thirdLayout.setGone()
                }

                2 -> {
                    thirdLayout.setGone()
                    secondLayout.setPadding(padding, 0, 0, 0)
                }

                3 -> {
                    secondLayout.setPadding(padding, 0, 0, 0)
                    thirdLayout.setPadding(0, padding, 0, 0)
                }
                else -> {
                    secondLayout.setPadding(padding, 0, 0, 0)
                    thirdLayout.setPadding(0, padding, 0, 0)

                    tvPhotoLayer.setVisible()
                    tvPhotoLayer.text = "+${momentInfo.photo.size - 3}"
                }
            }

            val photoBindingList = listOf(ivPhoto1, ivPhoto2, ivPhoto3)
            momentInfo.photo.forEachIndexed { index, url ->
                if (index < 3) {
                    photoBindingList[index].setImageWithUrl(url)
                }
            }
        }
    }

    companion object {
        private const val KEY_MOMENT = "KEY_MOMENT"

        fun getIntent(context: Context, moment: MomentModel): Intent {
            return Intent(context, SodosiCommentActivity::class.java).apply {
                putExtra(KEY_MOMENT, moment)
            }
        }
    }
}
