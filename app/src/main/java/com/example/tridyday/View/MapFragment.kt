package com.example.tridyday.View


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tridyday.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions



class data_schedule(){
    var data_set:Int = 0
    val data_num = Array<Int>(10) { 0 }
    val data_map = Array<String>(10){"데이터 없음"}
    val data_position = Array<String>(10){"37.5666, 126.979"}

    fun add_data(name:String, lat:Double, lng:Double){
        data_num[data_set] = 1
        data_map[data_set] = name
        data_position[data_set] = "$lat, $lng"
        data_set++
    }

    fun del_data(num:Int){  //구현 중
        data_num[num] = 0
        data_map[num] = "데이터 없음"

    }

}

class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var binding: FragmentMapBinding?= null
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater)

        this.mapView = binding!!.map01
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ds = data_schedule()
        ds.add_data("test0", 37.5666, 126.979)    //일정 데이터 받아올 예정
        ds.add_data("test1", 37.5666, 126.979)
        ds.add_data("test2", 37.5666, 126.979)
        ds.add_data("test3", 37.5666, 126.979)
        ds.add_data("test4", 37.5666, 126.979)
        ds.add_data("test5", 37.5666, 126.979)
        ds.add_data("test6", 37.5666, 126.979)
        ds.add_data("test7", 37.5666, 126.979)


        val linear = binding?.mySchedule

        val newButton = Array(10){Button(context)}

        for (i in 0 until ds.data_set) {
            newButton[i].text = ds.data_map[i]
            if (linear != null) {
                linear.addView(newButton[i])
            }
            newButton[i].setOnClickListener{
                newButton[i].text = "클릭함" // 각 데이터의 지도위치로 연결할 예정
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
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

}

/*
gradle:app에 디펜던시에
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

안드로이드매니페스트에 어플리케이션안엔
<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="EY AIzaSyCYuoY_K5jbcKHEk9I19e4f-moMvY0HlYE" />


Tool -> SDK Manager -> SDK Tools 순으로 들어가서
Google Play services를 체크하고 설치

 */

