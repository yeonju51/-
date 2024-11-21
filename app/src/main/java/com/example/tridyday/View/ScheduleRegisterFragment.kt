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
            val startHour = binding.timePickerStart.hour
            val startMinute = binding.timePickerStart.minute
            val endHour = binding.timePickerEnd.hour
            val endMinute = binding.timePickerEnd.minute

            val formattedStartTime = formatTime(startHour, startMinute)
            val formattedEndTime = formatTime(endHour, endMinute)

            val formattedTimeRange = "$formattedStartTime - $formattedEndTime"

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {

            }
        }
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val amPm = if (hour < 12) "AM" else "PM"
        val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
        return String.format("%02d:%02d %s", displayHour, minute, amPm)
    }


    companion object {


    }
}