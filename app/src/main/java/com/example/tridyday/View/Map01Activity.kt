package com.example.tridy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapOptions

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


class Map01Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map01)
        val intent02 = Intent(this, Map02Activity::class.java)

        val MapOption:NaverMapOptions = NaverMapOptions()
            .camera(CameraPosition(LatLng(35.1798159, 129.0750222), 15.0))

        val fm = supportFragmentManager
        val map01Fragment = fm.findFragmentById(R.id.map01) as MapFragment?
            ?: MapFragment.newInstance(MapOption).also {
                fm.beginTransaction().add(R.id.map01, it).commit()
            }

        val backhome: Button = findViewById(R.id.backhome)
        backhome.setOnClickListener{ finish() }

        val map01: View = findViewById(R.id.map01)
        map01.setOnClickListener{startActivity(intent02)}



        val bigLocalSpinner:Spinner = findViewById(R.id.biglocal)
        val bigLocalAdapter = ArrayAdapter.createFromResource(this, R.array.big_local, android.R.layout.simple_spinner_item)

        bigLocalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bigLocalSpinner.adapter = bigLocalAdapter


        val smallLocalSpinner:Spinner = findViewById(R.id.smalllocal)

        val smallLocalAdapter = ArrayAdapter.createFromResource(this, R.array.small_local1, android.R.layout.simple_spinner_item)
        smallLocalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        smallLocalSpinner.adapter = smallLocalAdapter

        var selectnum = "small_local1"

        /*
        bigLocalSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>,view:View,position:Int,id:Long){
                when(position){
                    1-> smallLocalAdapter
                    2->
                    else -> ArrayAdapter.createFromResource(this, R.array.small_local1, android.R.layout.simple_spinner_item)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }*/


        val ds = data_schedule()
        ds.add_data("test0", 37.5666, 126.979)    //일정 데이터 받아올 예정
        ds.add_data("test1", 37.5666, 126.979)
        ds.add_data("test2", 37.5666, 126.979)
        ds.add_data("test3", 37.5666, 126.979)
        ds.add_data("test4", 37.5666, 126.979)
        ds.add_data("test5", 37.5666, 126.979)
        ds.add_data("test6", 37.5666, 126.979)
        ds.add_data("test7", 37.5666, 126.979)


        val linear:LinearLayout = findViewById(R.id.mySchedule)

        val newButton = Array(10){Button(this)}

        for (i in 0 until ds.data_set) {
            newButton[i].text = ds.data_map[i]
            linear.addView(newButton[i])
            newButton[i].setOnClickListener{
                newButton[i].text = "클릭함" // 각 데이터의 지도위치로 연결할 예정
            }
        }


    }



}