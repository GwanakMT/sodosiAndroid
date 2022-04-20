package com.sodosi.ui.sodosi

import androidx.activity.viewModels
import com.skt.Tmap.TMapView
import com.sodosi.databinding.ActivityCreateMomentBinding
    import com.sodosi.ui.common.base.BaseActivity

class CreateMomentActivity : BaseActivity<CreateMomentViewModel, ActivityCreateMomentBinding>() {
    private lateinit var mapView: TMapView

    override fun getViewBinding() = ActivityCreateMomentBinding.inflate(layoutInflater)

    override val viewModel: CreateMomentViewModel by viewModels()

    override fun initViews() = with(binding) {
        initMapView()
        setOnClickListener()
    }

    override fun observeData() {

    }

    private fun initMapView() {
        mapView = TMapView(this);
        mapView.setSKTMapApiKey(SodosiActivity.MAP_API_KEY)

        binding.mapContainer.addView(mapView)
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener { onBackPressed() }
    }
}
