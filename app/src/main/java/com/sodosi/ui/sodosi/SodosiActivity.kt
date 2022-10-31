package com.sodosi.ui.sodosi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.location.LocationListener
import android.location.LocationManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapView
import com.sodosi.BuildConfig
import com.sodosi.R
import com.sodosi.databinding.ActivitySodosiBinding
import com.sodosi.databinding.LayoutSodosiMenuDialogBinding
import com.sodosi.databinding.LayoutSodosiReportDialogBinding
import com.sodosi.model.SodosiModel
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.base.repeatOnStarted
import com.sodosi.ui.common.customview.SodosiToast
import com.sodosi.ui.common.extensions.resize
import com.sodosi.ui.common.extensions.setGone
import com.sodosi.ui.create.EditSodosiActivity
import com.sodosi.ui.post.SearchPlaceActivity
import com.sodosi.ui.sodosi.bottomsheet.MomentBottomSheetFragment
import com.sodosi.ui.sodosi.bottomsheet.PlaceBottomSheetFragment
import com.sodosi.ui.sodosi.model.PlaceModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SodosiActivity : BaseActivity<SodosiViewModel, ActivitySodosiBinding>() {
    private val mapView: TMapView by lazy { TMapView(this) }
    private lateinit var menuDialog: Dialog
    private lateinit var reportDialog: Dialog
    private lateinit var firstSodosiDialog: Dialog

    private lateinit var defaultMarker: Bitmap
    private lateinit var hotMarker: Bitmap

    private val placeBottomSheet by lazy { PlaceBottomSheetFragment.newInstance(::moveToSearchPlaceActivityWithLocation) }
    private val momentBottomSheet by lazy { MomentBottomSheetFragment.newInstance(::onDismissMomentBottomSheet) }

    private lateinit var placeBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var momentBottomSheetBehavior: BottomSheetBehavior<View>

    private var sodosiInfo: SodosiModel? = null

    override fun getViewBinding() = ActivitySodosiBinding.inflate(layoutInflater)

    override val viewModel: SodosiViewModel by viewModels()

    private val locationManager: LocationManager by lazy { getSystemService(LOCATION_SERVICE) as LocationManager }
    private val locationListener = LocationListener { location ->
        mapView.setLocationPoint(location.longitude, location.latitude)
    }

    private val editSodosiLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            sodosiInfo = it.data?.getParcelableExtra(EditSodosiActivity.KEY_SODOSI_MODEL) as? SodosiModel
            binding.tvMapTitle.text = sodosiInfo?.name
        }
    }

    override fun initViews() = with(binding) {
        checkIsFirstSodosi()

        changeStatusBarColorWhite()
        sodosiInfo = intent.getParcelableExtra(EXTRA_SODOSI) as? SodosiModel

        binding.tvMapTitle.text = sodosiInfo?.name
        binding.tvMomentCount.text = getString(
            R.string.sodosi_moment_count,
            sodosiInfo?.momentCount
        )

        // 1) LocationManager 세팅하기
        initLocationManager()

        // 2) MapView 세팅하기
        initMapView()
        initPlaceBottomSheetBehavior()
        initMomentBottomSheetBehavior()
        setOnClickListener()
        initReportDialog()

        setResult(RESULT_OK)
    }

    override fun observeData() {
        repeatOnStarted {
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

        repeatOnStarted {
            viewModel.bookmarkEvent.collect {
                if (it) {
                    sodosiInfo?.let {
                        val currentBookmarkState = it.isMarked
                        sodosiInfo = it.copy(isMarked = !currentBookmarkState)

                        if (currentBookmarkState) {
                            SodosiToast.makeText(this@SodosiActivity, "관심 소도시에서 삭제했습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            SodosiToast.makeText(this@SodosiActivity, "관심 소도시에 등록했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    SodosiToast.makeText(this@SodosiActivity, "관심 소도시 등록/해제 실패...", Toast.LENGTH_SHORT).show()
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
        mapView.setSKTMapApiKey(MAP_API_KEY)

        // mapView 세팅
        binding.mapContainer.addView(mapView)
        mapView.mapType = TMapView.MAPTYPE_STANDARD
        mapView.setLanguage(TMapView.LANGUAGE_KOREAN)

        // 소도시에서 사용할 아이콘 설정
        val myLocationBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_point_icon).resize(32)
        mapView.setIcon(myLocationBitmap)
        mapView.setIconVisibility(true)

        defaultMarker = BitmapFactory.decodeResource(resources, R.drawable.map_marker_default_icon).resize(24)
        hotMarker = BitmapFactory.decodeResource(resources, R.drawable.map_marker_hot_icon).resize(24)

        // 현재 내 위치 표시
        val locationPoint = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        locationPoint?.let {
            mapView.setLocationPoint(it.longitude, it.latitude)
            mapView.setCenterPoint(it.longitude, it.latitude, false)
        }

        // 지도에 표시할 마커 목록 가져오기
        viewModel.getPlaceList()
    }

    private fun initLocationManager() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1.0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1.0f, locationListener)
    }

    private fun moveFocusToCenterPoint(animate: Boolean) {
        mapView.setCenterPoint(mapView.locationPoint.longitude, mapView.locationPoint.latitude, animate)
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
        binding.btnMenu.setOnClickListener {
            initMenuDialog()
            menuDialog.show()
        }
        binding.gpsEllipse.setOnClickListener { moveFocusToCenterPoint(true) }
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

            // initView
            if (sodosiInfo?.isMine == true) {
                binding.tvBookmark.setGone()
                binding.dividerBookmarkSodosi.setGone()

                binding.tvReport.setGone()
                binding.dividerShare.setGone()
            } else {
                binding.tvEditSodosi.setGone()
                binding.dividerEditSodosi.setGone()
            }

            if (sodosiInfo?.isMarked == true) {
                binding.tvBookmark.text = getString(R.string.sodosi_menu_bookmark_cancel)
                dismiss()
            }

            with(binding) {
                tvEditSodosi.setOnClickListener {
                    sodosiInfo?.let {
                        val intent = EditSodosiActivity.getIntent(this@SodosiActivity, it)
                        editSodosiLauncher.launch(intent)
                    }
                    menuDialog.dismiss()
                }

                tvBookmark.setOnClickListener {
                    sodosiInfo?.let { sodosi ->
                        viewModel.patchSodosi(sodosi.id, sodosi.isMarked)
                    }
                    menuDialog.dismiss()
                }

                tvShare.setOnClickListener {
                    // TODO
                    SodosiToast.makeText(this@SodosiActivity, "준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
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

    private fun moveToSearchPlaceActivityWithLocation() {
        val currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        currentLocation?.let {
            startActivity(SearchPlaceActivity.getIntent(this, it.longitude, it.latitude))
        }
    }

    private fun checkIsFirstSodosi() {
        val hasSodosiBefore = intent.getBooleanExtra(EXTRA_HAS_SODOSI, true)
        if (!hasSodosiBefore) {
            firstSodosiDialog = Dialog(this).apply {
                setContentView(R.layout.dialog_make_sodosi_first)

                findViewById<TextView>(R.id.btnNextStep).setOnClickListener {
                    firstSodosiDialog.dismiss()
                    moveToSearchPlaceActivityWithLocation()
                }

                findViewById<TextView>(R.id.btnPassStep).setOnClickListener {
                    firstSodosiDialog.dismiss()
                }

                window?.apply {
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setGravity(Gravity.CENTER)
                    setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
            }

            firstSodosiDialog.show()
        }
    }

    companion object {
        const val MAP_API_KEY = BuildConfig.TMAP_API_KEY

        private const val EXTRA_SODOSI = "EXTRA_SODOSI"
        private const val EXTRA_HAS_SODOSI = "EXTRA_HAS_SODOSI"

        fun getIntent(context: Context, sodosi: SodosiModel, hasSodosi: Boolean = true): Intent {
            return Intent(context, SodosiActivity::class.java).apply {
                putExtra(EXTRA_SODOSI, sodosi)
                putExtra(EXTRA_HAS_SODOSI, hasSodosi)
            }
        }
    }
}
