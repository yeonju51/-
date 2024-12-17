package com.example.tridyday.View

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var binding: FragmentMapBinding?= null
    private lateinit var mapView: MapView
    private lateinit var MygoogleMap: GoogleMap

    val viewModel: ViewModel by activityViewModels()

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

        //lateinit var scheduleList: List<Travel.Schedule>

        viewModel.schedules.observe(viewLifecycleOwner) { schedules ->

            Log.e("확인", " 체크 : $schedules")

            val newButton = Array(schedules.size){Button(context)}

            for (i in schedules.indices) {
                newButton[i].setPadding(0,0,0,0)
                newButton[i].text = (i+1).toString() + "번째 : " + schedules[i].locate.name

                binding?.mySchedule?.addView(newButton[i])

                newButton[i].setOnClickListener {
                    for(j in schedules.indices){
                        newButton[j].setBackgroundColor(Color.LTGRAY)
                    }
                    newButton[i].setBackgroundColor(Color.YELLOW)

                    moveMap(schedules[i].locate.lat, schedules[i].locate.lng)
                    markerMap(
                        schedules[i].locate.name,
                        schedules[i].locate.lat,
                        schedules[i].locate.lng
                    )
                }

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

