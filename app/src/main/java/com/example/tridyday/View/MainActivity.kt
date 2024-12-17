package com.example.tridyday.View

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
            if (destination.id == R.id.homeFragment
                || destination.id == R.id.travelRegistrationFragment
                || destination.id == R.id.loginFragment) {

                binding.botNav.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
            }
        }
        setContentView(binding.root)
    }
}
