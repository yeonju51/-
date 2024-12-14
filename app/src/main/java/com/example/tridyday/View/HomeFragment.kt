package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var adapter: TravelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // RecyclerView 설정
        adapter = TravelAdapter(mutableListOf())
        binding.adventureRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.adventureRecyclerView.adapter = adapter

        // LiveData 관찰하여 RecyclerView 업데이트
        viewModel.travels.observe(viewLifecycleOwner) { travels ->
            adapter.updateTravels(travels)
        }
    }
}
