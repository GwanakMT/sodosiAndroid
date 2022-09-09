package com.sodosi.ui.sodosi

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.location.LocationListener
import android.location.LocationManager
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.poi_item.TMapPOIItem
import com.sodosi.R
import com.sodosi.databinding.ActivityCreateMomentBinding
import com.sodosi.ui.common.base.BaseActivity
import com.sodosi.ui.common.extensions.resize
import com.sodosi.ui.sodosi.bottomsheet.CreateMomentBottomSheetFragment

class CreateMomentActivity : BaseActivity<CreateMomentViewModel, ActivityCreateMomentBinding>() {
    private val mapView: TMapView by lazy { TMapView(this) }
    private lateinit var createMomentBottomSheet: CreateMomentBottomSheetFragment
    private lateinit var createMomentBottomSheetBehavior: BottomSheetBehavior<View>

    private val locationManager: LocationManager by lazy { getSystemService(LOCATION_SERVICE) as LocationManager }
    private val locationListener = LocationListener { location ->
        mapView.setLocationPoint(location.longitude, location.latitude)
    }

    override fun getViewBinding() = ActivityCreateMomentBinding.inflate(layoutInflater)

    override val viewModel: CreateMomentViewModel by viewModels()

    override fun initViews() = with(binding) {
        initMapView()
        initBottomSheet()
        initCreateMomentBottomSheetBehavior()
        setListener()
    }

    override fun observeData() {

    }

    private fun initMapView() {
        mapView.setSKTMapApiKey(SodosiActivity.MAP_API_KEY)

        // LocationManager 세팅하기
        initLocationManager()

        // mapView 세팅
        binding.mapContainer.addView(mapView)
        mapView.mapType = TMapView.MAPTYPE_STANDARD
        mapView.setLanguage(TMapView.LANGUAGE_KOREAN)

        // 소도시에서 사용할 아이콘 설정
        val myLocationBitmap = BitmapFactory.decodeResource(resources, R.drawable.map_point_icon).resize(32)
        mapView.setIcon(myLocationBitmap)
        mapView.setIconVisibility(true)

        // 현재 내 위치에 표시
        val locationPoint = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        locationPoint?.let {
            mapView.setLocationPoint(it.longitude, it.latitude)
            mapView.setCenterPoint(it.longitude, it.latitude, false)
        }
    }

    private fun initLocationManager() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1.0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1.0f, locationListener)
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.gpsEllipse.setOnClickListener { moveFocusToCenterPoint() }

        mapView.setOnDisableScrollWithZoomLevelListener { zoomLevel, centerPoint ->
            setCurrentPlaceName(getPoiData(centerPoint.longitude, centerPoint.latitude))
        }

        mapView.setOnClickListenerCallBack(object : TMapView.OnClickListenerCallback {
            override fun onPressEvent(
                markerItemList: ArrayList<TMapMarkerItem>?,
                poiItemList: ArrayList<TMapPOIItem>?,
                point: TMapPoint?,
                pointF: PointF?
            ): Boolean {
                return true
            }

            override fun onPressUpEvent(
                markerItemList: ArrayList<TMapMarkerItem>?,
                poiItemList: ArrayList<TMapPOIItem>?,
                point: TMapPoint?,
                pointF: PointF?
            ): Boolean {
                val centerPoint = mapView.centerPoint
                setCurrentPlaceName(getPoiData(centerPoint.longitude, centerPoint.latitude))
                return true
            }
        })
    }

    private fun moveFocusToCenterPoint() {
        mapView.setTrackingMode(true)
        mapView.setCenterPoint(mapView.locationPoint.longitude, mapView.locationPoint.latitude, true)
    }

    private fun initBottomSheet() {
        val centerPoint = mapView.centerPoint
        val startPlace = getPoiData(centerPoint.longitude, centerPoint.latitude)
        createMomentBottomSheet = CreateMomentBottomSheetFragment.newInstance(startPlace)
        supportFragmentManager.beginTransaction()
            .replace(binding.createMomentBottomSheetContainer.id, createMomentBottomSheet)
            .commitNow()
    }

    private fun initCreateMomentBottomSheetBehavior() {
        createMomentBottomSheetBehavior = BottomSheetBehavior.from(binding.createMomentBottomSheetContainer)
        createMomentBottomSheetBehavior.apply {
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun getPoiData(longitude: Double, latitude: Double): String {
        // TODO: 위도, 경도 -> 주소(한글)
        val poiData = "$longitude, $latitude"
        return poiData
    }

    private fun setCurrentPlaceName(placeName: String) {
        createMomentBottomSheet.setCurrentPlaceName(placeName)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreateMomentActivity::class.java)
        }
    }
}
