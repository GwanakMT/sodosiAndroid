package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.sodosi.R
import com.sodosi.databinding.ActivityPostMomentBinding
import com.sodosi.model.POIDataModel
import com.sodosi.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostMomentActivity : BaseActivity<PostMomentViewModel, ActivityPostMomentBinding>() {
    override val viewModel: PostMomentViewModel by viewModels()
    private var momentPlace: POIDataModel? = null

    override fun getViewBinding() = ActivityPostMomentBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        momentPlace = intent.getParcelableExtra(KEY_MOMENT_PLACE) as? POIDataModel
        binding.tvPlaceName.text = momentPlace?.placeName

        initAppbar()
        initEditTextView()
        initPhotoRecyclerView()

        setListener()
    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }

            initAppbarTitle(getString(R.string.create_sodosi))
        }
    }

    private fun initEditTextView() {
        binding.etMoment.hint = getString(R.string.create_sodosi_name_hint)
        binding.etMoment.addTextChangedListener {
            if ("$it".length > 0) {
                binding.btnSubmit.setTextColor(Color.parseColor("#0F0F10"))
            } else {
                binding.btnSubmit.setTextColor(Color.parseColor("#ADADAD"))
            }
        }
    }

    private fun initPhotoRecyclerView() {
        // TODO: 이미지 가져오기 구현
    }

    private fun setListener() {
        binding.btnSubmit.setOnClickListener {

        }

        binding.photoCountLayout.setOnClickListener {
            // TODO: Move To Gallary
        }
    }

    companion object {
        private const val KEY_MOMENT_PLACE = "KEY_MOMENT_PLACE"

        fun getIntent(context: Context, momentPlace: POIDataModel): Intent {
            return Intent(context, PostMomentActivity::class.java).apply {
                putExtra(KEY_MOMENT_PLACE, momentPlace)
            }
        }
    }
}
