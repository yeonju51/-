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

    private val scheduleByDay = mutableMapOf<Int, MutableList<Travel.Schedule>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel에서 여행 목록을 관찰
        viewModel.travels.observe(viewLifecycleOwner) { travelList ->
            if (travelList.isNotEmpty()) {
                // 첫 번째 여행의 시작일과 종료일로 여행 일수 계산
                viewModel.calculateTravelDays()
            }
        }

        // 여행 일수 LiveData를 관찰하여 버튼 생성
        viewModel.travelDaysLiveData.observe(viewLifecycleOwner) { totalDays ->
            updateDayButtons(totalDays)
        }

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달된 여행 제목을 텍스트 뷰에 설정
        val travelTitle = arguments?.getString("travelTitle")
        binding.txtScheduleTitle.text = travelTitle

        // 스케줄 LiveData를 관찰하여 일정 데이터 업데이트
        viewModel.schedules.observe(viewLifecycleOwner) { schedules ->
            scheduleByDay.clear()
            schedules.forEach { schedule ->
                val day = schedule.day
                if (scheduleByDay[day] == null) {
                    scheduleByDay[day] = mutableListOf()
                }
                scheduleByDay[day]?.add(schedule)
            }

            // 첫 번째 Day의 스케줄을 기본으로 표시
            if (scheduleByDay.isNotEmpty()) {
                showDaySchedule(1)
            }
        }
    }

    /**
     * 특정 Day의 스케줄을 RecyclerView에 표시
     */
    private fun showDaySchedule(day: Int) {
        val schedulesForDay = scheduleByDay[day]
        if (schedulesForDay != null) {
            binding.recSchedule.adapter?.let {
                if (it is SchedulesAdapter) {
                    it.setSchedules(schedulesForDay)
                    it.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * 여행 일수에 따라 Day 버튼을 동적으로 생성
     */
    private fun updateDayButtons(totalDays: Int) {
        binding.buttonContainer.removeAllViews() // 기존 버튼 초기화

        if (totalDays > 0) {
            for (day in 1..totalDays) {
                val dayButton = Button(requireContext()).apply {
                    text = "Day $day"
                    setOnClickListener {
                        showDaySchedule(day)
                    }
                }
                binding.buttonContainer.addView(dayButton)
            }
        }
    }
}
