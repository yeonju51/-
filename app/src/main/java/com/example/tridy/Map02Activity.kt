package com.example.tridy

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment

class Map02Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map02)

        val fm = supportFragmentManager
        val map02Fragment = fm.findFragmentById(R.id.map02) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map02, it).commit()
            }
        val backhome2: Button = findViewById(R.id.backhome2)
        backhome2.setOnClickListener{ finish() }
    }

}