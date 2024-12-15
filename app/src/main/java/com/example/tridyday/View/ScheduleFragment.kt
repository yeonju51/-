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
    val viewModel: ViewModel by activityViewModels()

    private val scheduleByDay = mutableMapOf<Int, MutableList<Travel.Schedule>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // RecyclerView 설정
        val scheduleAdapter = SchedulesAdapter()
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = scheduleAdapter

        // 여행 데이터 관찰 (Repository에서 데이터 가져오기)
        viewModel.observeTravels()

        // 여행 일수에 따라 버튼을 동적으로 생성
        viewModel.travelDaysLiveData.observe(viewLifecycleOwner) { totalDays ->
            if (totalDays > 0) {
                binding.buttonContainer.removeAllViews() // 기존 버튼 초기화
                for (day in 1..totalDays) {
                    val dayButton = Button(requireContext()).apply {
                        text = "Day $day"
                        setOnClickListener {
                            showDaySchedule(day, scheduleAdapter)
                        }
                    }
                    binding.buttonContainer.addView(dayButton)
                }
            }
        }

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    // 여행 일수에 맞춰 일정을 보여주는 함수
    private fun showDaySchedule(day: Int, scheduleAdapter: SchedulesAdapter) {
        val schedulesForDay = scheduleByDay[day]
        if (schedulesForDay != null) {
            scheduleAdapter.setSchedules(schedulesForDay)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전달된 데이터 가져오기
        val travelTitle = arguments?.getString("travelTitle")
        binding.txtScheduleTitle.setText(travelTitle)

        // 일정 정보 가져오기
        viewModel.schedules.observe(viewLifecycleOwner) { schedules ->
            scheduleByDay.clear()
            schedules.forEach { schedule ->
                val day = schedule.day
                if (scheduleByDay[day] == null) {
                    scheduleByDay[day] = mutableListOf()
                }
                scheduleByDay[day]?.add(schedule)
            }

            // 일정이 존재하면 첫 번째 일정을 보여줌
            if (scheduleByDay.isNotEmpty()) {
                showDaySchedule(1, binding.recSchedule.adapter as SchedulesAdapter)
            }
        }
    }
}
