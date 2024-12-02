package com.example.tridyday.View

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding
import java.time.LocalTime

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register), TimePicker.OnTimeChangedListener {

    private lateinit var binding: FragmentScheduleRegisterBinding
    val viewModel: ViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.inflate(layoutInflater)

        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val memo = binding.txtMemo.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val startHour = binding.timePickerStart.hour
                val startMinute = binding.timePickerStart.minute
                val endHour = binding.timePickerEnd.hour
                val endMinute = binding.timePickerEnd.minute

                val startTime = LocalTime.of(startHour, startMinute)
                val endTime = LocalTime.of(endHour, endMinute)

                val newSchedule = Travel.Schedule(title, startTime, endTime, memo,
                    locate = Travel.Schedule.Locate(
                        name = "Sample Location",
                        iD = "1234",
                        lat = 37.5665,
                        lng = 126.9780,
                        place = "Seoul"
                    )
                )

                viewModel.addSchedule(newSchedule)

                // 완료 후 돌아가기
                findNavController().popBackStack()
            }
        }
    }

    override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}