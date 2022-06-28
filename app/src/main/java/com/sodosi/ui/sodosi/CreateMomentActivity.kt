package com.sodosi.ui.sodosi

import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapView
import com.sodosi.databinding.ActivityCreateMomentBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.sodosi.bottomsheet.CreateMomentBottomSheetFragment

class CreateMomentActivity : BaseActivity<CreateMomentViewModel, ActivityCreateMomentBinding>() {
    private lateinit var mapView: TMapView
    private val createMomentBottomSheet by lazy { CreateMomentBottomSheetFragment.newInstance() }
    private lateinit var createMomentBottomSheetBehavior: BottomSheetBehavior<View>

    override fun getViewBinding() = ActivityCreateMomentBinding.inflate(layoutInflater)

    override val viewModel: CreateMomentViewModel by viewModels()

    override fun initViews() = with(binding) {
        initMapView()
        initCreateMomentBottomSheetBehavior()
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

    private fun initCreateMomentBottomSheetBehavior() {
        supportFragmentManager.beginTransaction()
            .replace(binding.createMomentBottomSheetContainer.id, createMomentBottomSheet)
            .commitNow()

        createMomentBottomSheetBehavior = BottomSheetBehavior.from(binding.createMomentBottomSheetContainer)
        createMomentBottomSheetBehavior.apply {
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}
