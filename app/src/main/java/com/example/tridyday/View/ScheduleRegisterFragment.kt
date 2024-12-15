package com.example.tridyday.View

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding
import java.time.LocalTime

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register),
    TimePicker.OnTimeChangedListener {

    private lateinit var binding: FragmentScheduleRegisterBinding
    private val repository = Repository()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.bind(view)

        // 완료 버튼 클릭 이벤트
        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val day = binding.txtDay.text.toString().toIntOrNull()
            val memo = binding.txtMemo.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 시간 확인 및 Schedule 객체 생성
            val startTime = LocalTime.of(binding.timePickerStart.hour, binding.timePickerStart.minute)
            val endTime = LocalTime.of(binding.timePickerEnd.hour, binding.timePickerEnd.minute)

            if (endTime <= startTime) {
                Toast.makeText(requireContext(), "종료 시간이 시작 시간보다 늦어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val schedule = Travel.Schedule(
                title = title,
                day = day ?: 1,
                startTime = startTime,
                endTime = endTime,
                memo = memo,
                locate = Travel.Schedule.Locate("", "", 0.0, 0.0, "")
            )

            repository.postSchedule(schedule, onSuccess = {
                Toast.makeText(requireContext(), "일정이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)
            }, onFailure = {
                Toast.makeText(requireContext(), "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // 필요 시 구현
    }
}
