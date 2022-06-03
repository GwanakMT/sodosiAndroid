package com.sodosi.ui.sodosi

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapView
import com.sodosi.BuildConfig
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiBinding
import com.sodosi.databinding.LayoutSodosiMenuDialogBinding
import com.sodosi.databinding.LayoutSodosiReportDialogBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.resize
import com.sodosi.ui.sodosi.model.PlaceModel
import kotlinx.coroutines.launch

class SodosiActivity : BaseActivity<SodosiViewModel, ActivitySodosiBinding>() {
    private lateinit var mapView: TMapView
    private lateinit var menuDialog: Dialog
    private lateinit var reportDialog: Dialog

    private lateinit var defaultMarker: Bitmap
    private lateinit var hotMarker: Bitmap

    private val placeBottomSheet by lazy { PlaceBottomSheetFragment() }
    private val momentBottomSheet by lazy { MomentBottomSheetFragment.newInstance(::onDismissMomentBottomSheet) }

    private lateinit var placeBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var momentBottomSheetBehavior: BottomSheetBehavior<View>

    override fun getViewBinding() = ActivitySodosiBinding.inflate(layoutInflater)

    override val viewModel: SodosiViewModel by viewModels()

    override fun initViews() = with(binding) {
        changeStatusBarColorWhite()
        binding.tvMapTitle.text = intent.getStringExtra(EXTRA_MAP_NAME)
        binding.tvMomentCount.text = getString(
            R.string.sodosi_moment_count,
            intent.getIntExtra(EXTRA_MOMENT_COUNT, 0).toString()
        )

        initMapView()
        initPlaceBottomSheetBehavior()
        initMomentBottomSheetBehavior()
        setOnClickListener()
        initMenuDialog()
        initReportDialog()
    }

    override fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.placeList.collect {
                    it.forEach { place ->
                        mapView.addMarkerItem(place.id + place.placeName, TMapMarkerItem().apply {
                            longitude = place.longitude
                            latitude = place.latitude
                            icon = defaultMarker
                        })
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (placeBottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            onDismissMomentBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    private fun initMapView() {
        mapView = TMapView(this);
        mapView.setSKTMapApiKey(MAP_API_KEY)

        binding.mapContainer.addView(mapView)

        // 현재 위치 표시하는 아이콘 설정
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.map_point_icon).resize(32)
        mapView.setIcon(bitmap)
        mapView.setIconVisibility(true)

        // 지도에 표시할 마커 목록 가져오기
        defaultMarker = BitmapFactory.decodeResource(resources, R.drawable.map_marker_default_icon).resize(24)
        hotMarker = BitmapFactory.decodeResource(resources, R.drawable.map_marker_hot_icon).resize(24)

        viewModel.getPlaceList()

        // TODO
        // 1) 현재 내 위치 표시 (focus) + 커스텀할 수 있는지
        // 2) Marker 찍기 + Marker 이미지 커스텀
        // 3) Marker 클릭 이벤트 + BS 띄우기
        // 4) GeoCoding : 장소 기준은 장소명인가, address인가 ex) 신명아파트에 대한 리뷰인지, 창현로 60에 대한 리뷰인지
        // 5) 나침반모드 고정? 시야 표출?
    }

    private fun setGpsCenter() {
        mapView.setTrackingMode(true)
        mapView.setCenterPoint(mapView.locationPoint.longitude, mapView.locationPoint.latitude)
    }

    private fun initPlaceBottomSheetBehavior() {
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)

        supportFragmentManager.beginTransaction()
            .replace(binding.placeBottomSheetContainer.id, placeBottomSheet)
            .commitNow()

        placeBottomSheetBehavior = BottomSheetBehavior.from(binding.placeBottomSheetContainer)
        placeBottomSheetBehavior.apply {
            isHideable = false
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = resources.getDimensionPixelSize(R.dimen.place_bottom_sheet_peek_height)

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun initMomentBottomSheetBehavior() {
        // TODO: 이 사이즈는 뭐할 때 쓰는거지
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)

        supportFragmentManager.beginTransaction()
            .replace(binding.momentBottomSheetContainer.id, momentBottomSheet)
            .commitNow()

        momentBottomSheetBehavior = BottomSheetBehavior.from(binding.momentBottomSheetContainer)
        momentBottomSheetBehavior.apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = resources.getDimensionPixelSize(R.dimen.moment_bottom_sheet_peek_height)

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun setOnClickListener() {
        binding.placeBottomSheetContainer.setOnClickListener { }
        binding.momentBottomSheetContainer.setOnClickListener { }
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnMenu.setOnClickListener { menuDialog.show() }
        binding.gpsEllipse.setOnClickListener { setGpsCenter() }
    }

    fun showMomentBottomSheet(model: PlaceModel) {
        placeBottomSheetBehavior.isHideable = true
        placeBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        momentBottomSheetBehavior.isHideable = false
        momentBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        momentBottomSheet.setPlaceData(model)
    }

    private fun onDismissMomentBottomSheet() {
        placeBottomSheetBehavior.isHideable = false
        placeBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        momentBottomSheetBehavior.isHideable = true
        momentBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initMenuDialog() {
        menuDialog = Dialog(this)
        menuDialog.apply {
            val binding = LayoutSodosiMenuDialogBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding) {
                tvBookmark.setOnClickListener {

                }

                tvShare.setOnClickListener {

                }

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
                SodosiToast.makeText(context, getString(R.string.report_submit), Toast.LENGTH_SHORT)
                    .show()
            }

            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                attributes.windowAnimations = R.style.BottomDialogAnimation
            }
        }
    }

    companion object {
        const val MAP_API_KEY = BuildConfig.TMAP_API_KEY

        const val EXTRA_MAP_ID = "EXTRA_MAP_ID"
        const val EXTRA_MAP_NAME = "EXTRA_MAP_NAME"
        const val EXTRA_MOMENT_COUNT = "EXTRA_MOMENT_COUNT"
    }
}
