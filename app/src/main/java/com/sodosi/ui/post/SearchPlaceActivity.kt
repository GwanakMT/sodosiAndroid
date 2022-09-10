package com.sodosi.ui.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.skt.Tmap.TMapData
import com.sodosi.R
import com.sodosi.databinding.ActivitySearchPlaceBinding
import com.sodosi.ui.common.base.BaseActivity

class SearchPlaceActivity : BaseActivity<SearchPlaceViewModel, ActivitySearchPlaceBinding>() {
    private val tMapData: TMapData by lazy { TMapData() }

    override val viewModel: SearchPlaceViewModel by viewModels()

    override fun getViewBinding(): ActivitySearchPlaceBinding =
        ActivitySearchPlaceBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }

    override fun initViews() = with(binding) {
        initAppbar()
    }

    override fun observeData() {

    }

    private fun initAppbar() {
        binding.appbar.apply {
            initLeftButton(R.drawable.ic_arrow_left) {
                onBackPressed()
            }
        }
    }

    companion object {
        private val KEY_CURRENT_LONGITUDE = "KEY_CURRENT_LONGITUDE"
        private val KEY_CURRENT_LATITUDE = "KEY_CURRENT_LATITUDE"

        fun getIntent(context: Context, currentLongitude: Double, currentLatitude: Double): Intent {
            return Intent(context, SearchPlaceActivity::class.java).apply {
                putExtra(KEY_CURRENT_LONGITUDE, currentLongitude)
                putExtra(KEY_CURRENT_LATITUDE, currentLatitude)
            }
        }
    }
}
