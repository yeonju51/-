package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        adapter = TravelAdapter(mutableListOf()) { travel ->
            // 클릭된 여행 데이터를 Bundle에 담아 ScheduleFragment로 이동
            val bundle = Bundle().apply {
                putString("travelTitle", travel.title)
                putString("travelLocation", travel.location)
            }

            //맵프래그먼트에서 Travel ID 를 받을예정
            viewModel.sendSelTravel(1)
            // 수정된 부분: ScheduleFragment로 이동
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment, bundle)
        }
        binding.adventureRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.adventureRecyclerView.adapter = adapter

        // LiveData 관찰하여 RecyclerView 업데이트
        viewModel.travels.observe(viewLifecycleOwner) { travels ->
            adapter.updateTravels(travels)
        }

        // Add 버튼 클릭 시 TravelRegistrationFragment로 이동
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }
}
