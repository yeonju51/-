package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        // 연속적인 속성 설정을 한 번에 처리하도록 apply
        travelAdapter = TravelAdapter(mutableListOf())
        binding.adventureRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = travelAdapter
        }

        // travels 데이터가 변경되면 자동으로 호출되어 UI를 갱신
        // ViewModel의 travels 데이터를 관찰하여 UI 업데이트
        // travels는 최신 여행 데이터를 받고, 이를 UI에 반영하는 작업을 수행
        viewModel.travels.observe(viewLifecycleOwner, Observer { travels ->
            travelAdapter.updateTravels(travels)
        })

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }
}
