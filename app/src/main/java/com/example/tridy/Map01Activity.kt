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
    var data_num = Array<Int>(10) { 0 }
    var data_map = Array<String>(10){"데이터 없음"}

    fun add_data(name:String){
        data_num[data_set] = 1
        data_map[data_set] = name
        data_set++
    }

    fun del_data(num:Int){
        data_num[num] = 0
        data_map[num] = "데이터 없음"

    }

}

class Map01Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map01)
        val intent02 = Intent(this, Map02Activity::class.java)

        val fm = supportFragmentManager
        val map01Fragment = fm.findFragmentById(R.id.map01) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map01, it).commit()
            }

        val backhome: Button = findViewById(R.id.backhome)
        backhome.setOnClickListener{ finish() }

        val map01: View = findViewById(R.id.map01)
        map01.setOnClickListener{startActivity(intent02)}

        val MapOption = NaverMapOptions()
            .camera(CameraPosition(LatLng(53.1798159, 229.0750222), 8.0))

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
        ds.add_data("test0")
        ds.add_data("test1")
        ds.add_data("test2")
        ds.add_data("test3")
        ds.add_data("test4")
        ds.add_data("test5")
        ds.add_data("test6")
        ds.add_data("test7")


        val linear:LinearLayout = findViewById(R.id.mySchedule)
        val newButton0 = Button(this)
        val newButton1 = Button(this)
        val newButton2 = Button(this)
        val newButton3 = Button(this)
        val newButton4 = Button(this)
        val newButton5 = Button(this)
        val newButton6 = Button(this)
        val newButton7 = Button(this)


        newButton0.text = ds.data_map[0]
        newButton1.setText(ds.data_map[1])
        newButton2.setText(ds.data_map[2])
        newButton3.setText(ds.data_map[3])
        newButton4.setText(ds.data_map[4])
        newButton5.setText(ds.data_map[5])
        newButton6.setText(ds.data_map[6])
        newButton7.setText(ds.data_map[7])


        linear.addView(newButton0)
        linear.addView(newButton1)
        linear.addView(newButton2)
        linear.addView(newButton3)
        linear.addView(newButton4)
        linear.addView(newButton5)
        linear.addView(newButton6)
        linear.addView(newButton7)
    }


}