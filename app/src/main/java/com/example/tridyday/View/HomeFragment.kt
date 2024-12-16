package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var adapter: TravelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // RecyclerView 설정
        adapter = TravelAdapter(mutableListOf()) { travel ->
            // 선택한 여행 데이터를 SharedViewModel에 설정
            viewModel.selectedTravelId = travel.id

            // ScheduleFragment로 이동
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
        }

        binding.adventureRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.adventureRecyclerView.adapter = adapter

        // LiveData 관찰하여 RecyclerView 업데이트
        viewModel.travels.observe(viewLifecycleOwner) { travels ->
            adapter.updateTravels(travels.map { travel ->
                // 날짜 포맷팅
                travel.copy(
                    startDate = formatDate(travel.startDate),
                    endDate = formatDate(travel.endDate)
                )
            })
        }

        // Add 버튼 클릭 시 TravelRegistrationFragment로 이동
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }

    private fun formatDate(date: String?): String {
        if (date.isNullOrEmpty()) return ""
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate ?: date)
        } catch (e: Exception) {
            date ?: ""
        }
    }
}
