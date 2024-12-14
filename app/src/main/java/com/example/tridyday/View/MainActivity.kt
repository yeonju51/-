package com.example.tridyday.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tridyday.R
import com.example.tridyday.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val navController = supportFragmentManager.findFragmentById(R.id.frgNav) as NavHostFragment
        binding.botNav.setupWithNavController(navController.navController)
        navController.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.travelRegistrationFragment ) {
                binding.botNav.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
            }
        }
        setContentView(binding.root)
    }
}

//하단 네비게이션 바를 제어하는 목적의 파일
// 하단 네비게이션 바는 **homeFragment**나 **travelRegistrationFragment**에서는 숨기고, 그 외 화면에서는 보이도록 설정