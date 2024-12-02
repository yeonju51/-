package com.example.tridyday.View

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var travelAdapter: TravelAdapter
    private val viewModel: ViewModel by viewModels() // 기존 ViewModel 사용

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // RecyclerView 설정
        travelAdapter = TravelAdapter(mutableListOf())
        binding.adventureRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = travelAdapter
        }

        // ViewModel의 travels 데이터를 관찰하여 UI 업데이트
        viewModel.travels.observe(viewLifecycleOwner, Observer { travels ->
            travelAdapter.updateTravels(travels)
        })

        binding.addButton.setOnClickListener {
            Log.d("NavigationDebug", "Current Destination: ${findNavController().currentDestination?.id}")

            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }
}
