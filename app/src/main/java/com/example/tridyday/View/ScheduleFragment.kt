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

    private val schedules = mutableListOf<Travel.Schedule>()
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // RecyclerView 설정
        val scheduleAdapter = SchedulesAdapter(schedules)
        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = scheduleAdapter

        viewModel.travels.observe(viewLifecycleOwner) { travels ->
            // 여행 리스트가 변경되면 첫 번째 여행 제목을 txtScheduleTitle에 설정
            if (travels.isNotEmpty()) {
                binding.txtScheduleTitle.text = travels[0].title // 첫 번째 여행의 제목을 설정
            }
        }

        // 여행 데이터 관찰 (Repository에서 데이터 가져오기)
        viewModel.observeTravels() // Repository에서 데이터를 가져오고, ViewModel에서 처리하도록 함

        // 여행 일수에 따라 버튼을 동적으로 생성
        viewModel.travelDaysLiveData.observe(viewLifecycleOwner) { totalDays ->
            if (totalDays > 0) {
                binding.buttonContainer.removeAllViews() // 기존 버튼 초기화
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

    // 여행 일수에 맞춰 일정을 보여주는 함수
    private fun showDaySchedule(day: Int) {
        val schedulesForDay = schedules.filter { it.day == day }
        if (schedulesForDay.isNotEmpty()) {
            val adapter = binding.recSchedule.adapter
            if (adapter is SchedulesAdapter) {
                adapter.setSchedules(schedulesForDay)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedViewModel의 데이터를 관찰
        viewModel.selectedTravel.observe(viewLifecycleOwner) { travel ->
            // 선택된 여행 데이터를 가져와 화면에 표시
            schedules.clear()
            schedules.addAll(schedules)
            binding.recSchedule.adapter?.notifyDataSetChanged()
        }

        // 일정 추가 버튼 클릭 이벤트
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        // Day 버튼 동적 생성 및 클릭 이벤트
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
    }
}

