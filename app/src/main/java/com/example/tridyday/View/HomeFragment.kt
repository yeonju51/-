package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val travelList = MutableLiveData<MutableList<Travel>>()
    private lateinit var travelAdapter: TravelAdapter
    private val repository = Repository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        travelAdapter = TravelAdapter(mutableListOf())
        binding.adventureRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = travelAdapter
        }

        repository.observeTravels(travelList)

        travelList.observe(viewLifecycleOwner, Observer { travels ->
            travelAdapter.updateTravels(travels)
        })

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }
}
