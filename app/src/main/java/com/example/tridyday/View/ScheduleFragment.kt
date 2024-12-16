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

        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scheduleAdapter = SchedulesAdapter(schedules)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = scheduleAdapter

        viewModel.selectedTravel.observe(viewLifecycleOwner) { selectedTravel ->
            // selectedTravel이 변경될 때 UI를 업데이트합니다.
            if (selectedTravel != null) {
                binding.txtScheduleTitle.text = selectedTravel.title
                schedules.clear()
                schedules.addAll(schedules)
                scheduleAdapter.notifyDataSetChanged()

                val totalDays = if (selectedTravel.startDate != null && selectedTravel.endDate != null) {
                    viewModel.calculateDaysBetween(selectedTravel.startDate!!, selectedTravel.endDate!!)
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
            } else {
                // 선택된 여행이 없다면, 에러 처리나 기본 UI 처리
            }
        };
    }

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
