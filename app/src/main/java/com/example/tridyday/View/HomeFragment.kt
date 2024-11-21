package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentHomeBinding
import com.example.tridyday.Model.Travel
import com.example.tridyday.View.TravelAdapter  // TravelAdapter 임포트 추가

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val travelList = mutableListOf<Travel>()
    private lateinit var travelAdapter: TravelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // RecyclerView setup
        travelAdapter = TravelAdapter(travelList)
        binding.adventureRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = travelAdapter
        }

        // Add Button
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }

    fun addTravel(travel: Travel) {
        travelList.add(travel)
        travelAdapter.notifyItemInserted(travelList.size - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
