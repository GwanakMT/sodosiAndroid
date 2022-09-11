package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.sodosi.databinding.ActivityPostMomentBinding
import com.sodosi.model.POIDataModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostMomentActivity : BaseActivity<PostMomentViewModel, ActivityPostMomentBinding>() {
    override val viewModel: PostMomentViewModel by viewModels()

    override fun getViewBinding() = ActivityPostMomentBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() {
        val momentPlace = intent.getParcelableExtra<POIDataModel>(KEY_MOMENT_PLACE) as POIDataModel
        SodosiToast.makeText(this, "place: ${momentPlace.placeName}", Toast.LENGTH_SHORT).show()
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
