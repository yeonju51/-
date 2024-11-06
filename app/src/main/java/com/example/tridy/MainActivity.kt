package com.example.tridy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent01 = Intent(this, Map01Activity::class.java)
        val button : Button = findViewById(R.id.button)
        button.setOnClickListener{startActivity(intent01)}

        val intent02 = Intent(this, Map02Activity::class.java)
        val button2 : Button = findViewById(R.id.button2)
        button2.setOnClickListener{startActivity(intent02)}

        val intent03 = Intent(this, Map03Activity::class.java)
        val button3 : Button = findViewById(R.id.button3)
        button3.setOnClickListener{startActivity(intent03)}

        val intent04 = Intent(this, Map04Activity::class.java)
        val button4 : Button = findViewById(R.id.button4)
        button4.setOnClickListener{startActivity(intent04)}

        //네이버  api
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1apclb6cvd")


    }
}