package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    val viewModel1: ViewModel by activityViewModels()
    val viewModel2: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment 레이아웃을 결합
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        // binding.recSchedule.adapter = SchedulesAdapter(schedules)

        // 여행의 일수에 따라 Day 버튼 동적으로 추가
        val totalDays = 5 // 일단 5일 설정
        for (day in 1..totalDays) {
            val dayButton = Button(requireContext()).apply {
                text = "Day $day"
                setOnClickListener {
                    // Day 버튼 클릭 시 동작 설정
                }
            }
            binding.buttonContainer.addView(dayButton)
        }

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel1.schedule.observe(viewLifecycleOwner) {
            binding?.txtScheduleTitle.text = viewModel1.schedule.
        }
    }
}

