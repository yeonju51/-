package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tridyday.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class data_schedule(){
    var data_set:Int = 0
    val data_num = Array<Int>(10) { 0 }
    val data_map = Array<String>(10){"데이터 없음"}
    val data_lat = Array<Double>(10){37.5666}
    val data_lng = Array<Double>(10){126.979}

    fun add_data(name:String, lat:Double, lng:Double){
        data_num[data_set] = 1
        data_map[data_set] = name
        data_lat[data_set] = lat
        data_lng[data_set] = lng
        data_set++
    }
}

class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var binding: FragmentMapBinding?= null
    private lateinit var mapView: MapView
    private lateinit var MygoogleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater)

        mapView = binding!!.map01
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ds = data_schedule()
        ds.add_data("test0", 37.4666, 126.979)    //일정 데이터 받아올 예정
        ds.add_data("test1", 37.5666, 126.879)
        ds.add_data("test2", 37.6666, 126.779)
        ds.add_data("test3", 37.7666, 126.679)
        ds.add_data("test4", 37.8666, 126.579)
        ds.add_data("test5", 37.9666, 126.479)
        ds.add_data("test6", 37.0666, 126.379)
        ds.add_data("test7", 37.1666, 126.279)


        val newButton = Array(10){Button(context)}

        for (i in 0 until ds.data_set) {
            newButton[i].text = ds.data_map[i]
            binding?.mySchedule?.addView(newButton[i])
            newButton[i].setOnClickListener {
                newButton[i].text = "클릭함" // 각 데이터의 지도위치로 연결할 예정
                moveMap(ds.data_lat[i], ds.data_lng[i])
                markerMap(ds.data_map[i],ds.data_lat[i],ds.data_lng[i])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val marker = LatLng(37.568291,126.997780)
        MygoogleMap = googleMap
        googleMap.addMarker(MarkerOptions().position(marker).title("여기"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }

    fun moveMap(lat: Double, lng:Double){
        MygoogleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat,lng)))
    }

    fun markerMap(name:String, lat:Double, lng:Double){
        val markerOptions = MarkerOptions() // 마커 위치
            .position(LatLng(lat, lng))
            .title(name) // 말풍선 주 내용
            .snippet("Really Great!") // 말풍선 보조내용
        // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
        MygoogleMap.addMarker(markerOptions)?.showInfoWindow()
    }

}

/*
gradle:app에 디펜던시에
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

안드로이드매니페스트에 어플리케이션안엔
<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="API_KEY" />

<meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />


Tool -> SDK Manager -> SDK Tools 순으로 들어가서
Google Play services를 체크하고 설치

 */

