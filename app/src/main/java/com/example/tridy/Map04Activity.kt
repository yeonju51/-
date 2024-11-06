package com.example.tridy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment

class Map04Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map04)

        val fm = supportFragmentManager
        val map04Fragment = fm.findFragmentById(R.id.map04) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map04, it).commit()
            }

    }

}