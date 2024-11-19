package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Schedule
import com.example.tridyday.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    val schedules = arrayOf(
        Schedule("호텔 조식", "9:00 AM", "10:00 AM"),
        Schedule("초당 두부 마을", "10:00 AM", "11:00 AM"),
        Schedule("두부 만들기 체험", "11:00 AM", "1:00 PM"),
    )

    private lateinit var binding: FragmentScheduleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment 레이아웃을 결합
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.recSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recSchedule.adapter = SchedulesAdapter(schedules)

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
            replaceFragment(ScheduleRegisterFragment())
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().run {
            replace(binding.frmFrag.id, fragment)
            commit()
        }
    }
}