package com.sodosi.ui.post

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


}
