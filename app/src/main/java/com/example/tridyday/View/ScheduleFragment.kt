package com.example.tridyday.View

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private val viewModel: ViewModel by activityViewModels()
    private val schedules = mutableListOf<Travel.Schedule>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val travelId = viewModel.selectedTravelId // 선택된 여행 ID 가져오기

        // RecyclerView 설정
        val scheduleAdapter = SchedulesAdapter(schedules)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = scheduleAdapter

        // 여행 데이터를 관찰하여 화면 업데이트
        viewModel.selectedTravel.observe(viewLifecycleOwner) { travel ->
            binding.txtScheduleTitle.text = travel.title // 여행 제목 설정
            schedules.clear()
            schedules.addAll(schedules)
            scheduleAdapter.notifyDataSetChanged()
        }

        // 여행 일수에 따라 버튼 동적 생성
        viewModel.selectedTravel.observe(viewLifecycleOwner) { travel ->
            val totalDays = if (travel.startDate != null && travel.endDate != null) {
                viewModel.calculateDaysBetween(travel.startDate!!, travel.endDate!!)
            } else {
                0
            }
            binding.buttonContainer.removeAllViews()

            for (day in 1..totalDays) {
                val dayButton = Button(requireContext()).apply {
                    text = "Day $day"
                    setOnClickListener { showDaySchedule(day) }
                }
                binding.buttonContainer.addView(dayButton)
            }
        }

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    // 특정 Day에 해당하는 일정만 보여주는 함수
    private fun showDaySchedule(day: Int) {
        val schedulesForDay = schedules.filter { it.day == day }
        if (schedulesForDay.isNotEmpty()) {
            val adapter = binding.recSchedule.adapter
            if (adapter is SchedulesAdapter) {
                adapter.setSchedules(schedulesForDay)
            }
        }
    }
}
