package com.example.tridyday.View


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tridyday.databinding.FragmentRegistMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RegistMapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var binding: FragmentRegistMapBinding?= null
    private lateinit var mapView: MapView
    private lateinit var MygoogleMap: GoogleMap



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistMapBinding.inflate(inflater)

        mapView = binding!!.map02
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    fun moveMap(latitude: Double, longitude:Double){
        MygoogleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
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

