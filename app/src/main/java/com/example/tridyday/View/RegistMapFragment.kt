package com.example.tridyday.View


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentRegistMapBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener




class RegistMapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private var binding: FragmentRegistMapBinding?= null
    private lateinit var mapView: MapView
    private lateinit var MygoogleMap: GoogleMap


    private var placeID: String = ""
    private var placeName = ""
    private var placeLat: Double = 0.0
    private var placeLng: Double = 0.0
    private var placeLocate =""

    val viewModel: ViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistMapBinding.inflate(inflater)

        mapView = binding!!.map02
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the SDK
        Places.initializeWithNewPlacesApiEnabled(context, "AIzaSyBQ6hR87-xJCOdkAEIT1KNBh4rWMPOW4HU")

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // place data중 반환될 데이터 설정
        autocompleteFragment.setPlaceFields(listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG))

        // PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.

                Log.i( tag,"Place: ${place.id} ${place.latLng} ${place.name} ${place.address}")   //, ${place.location}
                //Place: ChIJ1bj7UAChfDURKL0Z-CBw5ic lat/lng: (37.4956492,127.0281847) DF타워 대한민국 서울특별시 서초구 강남대로 369

                placeID = place.id.toString()
                placeName = place.name.toString()
                val LatLngSplit = place.latLng.toString().split(",")

                placeLat = LatLngSplit[0].replace(("lat/lng: ("), "").toDouble()
                placeLng = LatLngSplit[1].replace((")"), "").toDouble()

                placeLocate = place.address.toString()


                renewal(placeName,placeLocate) // 데이터로 텍스트 넘어감
                moveMap(placeLng,placeLat)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i( tag,"An error occurred: $status")
            }
        })

        //지역 제한 한국으로
        autocompleteFragment.setCountries("KR")


        binding?.registBtn?.setOnClickListener{
            if(placeID != "") {

                viewModel.locate.observe(viewLifecycleOwner){
                    //읽어올 내용 binding?.txt.text = viewModel.Locate.value
                    viewModel.setLocate(placeName,placeID,placeLat,placeLng,placeLocate)
                }
                //childFragmentManager.popBackStack()
                activity?.onBackPressedDispatcher?.onBackPressed()
                //findNavController().navigate(R.id.action_registMapFragment_to_registScheduleFragment)
            }
            else{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("")
                    .setMessage("위치가 지정되지 않았습니다.")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                builder.show()
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

    fun moveMap(latitude: Double, longitude:Double){
        Log.i( tag,"moveMap 체크용: ${LatLng(longitude, latitude)}  ${placeLat} ${placeLng}")
        MygoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(longitude,latitude), 16f))
    }

    fun markerMap(name:String, lat:Double, lng:Double){
        val markerOptions = MarkerOptions() // 마커 위치
            .position(LatLng(lat, lng))
            .title(name) // 말풍선 주 내용
            .snippet("Really Great!") // 말풍선 보조내용
        // 마커를 추가하고 말풍선 표시한 것을 보여주도록 호출
        MygoogleMap.addMarker(markerOptions)?.showInfoWindow()
    }

    fun renewal(name:String, locate:String){
        if(binding != null) {
            binding!!.placeName.text = name
            binding!!.placeLocation.text = locate
        }
    }
}
/*
디펜던시에 추가
implementation(libs.places)
 */

