package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())

        viewModel.travelDaysLiveData.observe(viewLifecycleOwner) { totalDays ->
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

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    private fun showDaySchedule(day: Int) {
        val schedulesForDay = scheduleByDay[day]
        if (schedulesForDay != null) {
            binding.recSchedule.adapter?.let {
                if (it is SchedulesAdapter) {
                    it.setSchedules(schedulesForDay)
                    it.notifyDataSetChanged()  // 데이터 변경 알림
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 전달된 데이터 가져오기
        val travelTitle = arguments?.getString("travelTitle")

        binding.txtScheduleTitle.setText(travelTitle)

        viewModel.schedules.observe(viewLifecycleOwner) { schedules ->
            scheduleByDay.clear()
            schedules.forEach { schedule ->
                val day = schedule.day
                if (scheduleByDay[day] == null) {
                    scheduleByDay[day] = mutableListOf()
                }
                scheduleByDay[day]?.add(schedule)
            }

            if (scheduleByDay.isNotEmpty()) {
                showDaySchedule(1)
            }
        }
    }
}