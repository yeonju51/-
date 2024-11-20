package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register) {

    private lateinit var binding: FragmentScheduleRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.inflate(layoutInflater)

        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val selectedHour = binding.timePicker.hour
            val selectedMinute = binding.timePicker.minute
            // AM, PM 설정
            val amPm = if (selectedHour < 12) "AM" else "PM"
            // 12시간 형식으로 변환
            val displayHour = if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
            val formattedTime = String.format("%02d:%02d %s", displayHour, selectedMinute, amPm)
            val memo = binding.txtMemo.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 데이터 전달 및 이동
                val action = ScheduleRegisterFragmentDirections
                    .actionScheduleRegisterFragmentToScheduleFragment(
                        scheduleTitle = title,
                        scheduleHour = selectedHour,
                        scheduleMinute = selectedMinute,
                        scheduleTime = formattedTime,
                        scheduleMemo = memo
                    )
                findNavController().navigate(action)
            }
        }
    }


    companion object {


    }
}