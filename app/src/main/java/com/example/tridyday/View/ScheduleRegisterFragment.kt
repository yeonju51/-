package com.example.tridyday.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tridyday.Model.Schedule
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register), TimePicker.OnTimeChangedListener {

    private lateinit var binding: FragmentScheduleRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.inflate(layoutInflater)

        // TimePicker 시작 시간 리스너 설정
        binding.timePickerStart.setOnTimeChangedListener { _, hourOfDay, minute ->
            // 시작 시간 처리
            val startTime = "$hourOfDay:$minute"
            Log.d("StartTime", "Start Time: $startTime")
        }

        // TimePicker 종료 시간 리스너 설정
        binding.timePickerEnd.setOnTimeChangedListener { _, hourOfDay, minute ->
            // 종료 시간 처리
            val endTime = "$hourOfDay:$minute"
            Log.d("EndTime", "End Time: $endTime")
        }

        // 완료 버튼 클릭 리스너 설정
        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val memo = binding.txtMemo.text.toString()

            // 입력값 검증
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val startTime = "${binding.timePickerStart.hour}:${binding.timePickerStart.minute}"
                val endTime = "${binding.timePickerEnd.hour}:${binding.timePickerEnd.minute}"

                // Schedule 객체 생성
                val schedule = Schedule(title, startTime, endTime, memo)

            }
        }
    }

    companion object {
        // 필요한 컴패니언 객체 작업
    }

    override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {

    }
}