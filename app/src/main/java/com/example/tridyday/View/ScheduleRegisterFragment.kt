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
        binding = FragmentScheduleRegisterBinding.bind(view)

        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val day = binding.txtDay.text.toString().toIntOrNull()
            val memo = binding.txtMemo.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (day == null) {
                Toast.makeText(requireContext(), "몇째 날인지 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                val startTime = LocalTime.of(binding.timePickerStart.hour, binding.timePickerStart.minute)
                val endTime = LocalTime.of(binding.timePickerEnd.hour, binding.timePickerEnd.minute)

                if (endTime.isBefore(startTime) || endTime == startTime) {
                    Toast.makeText(requireContext(), "종료 시간이 시작 시간보다 늦어야 합니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.setSchedule(title, day, startTime, endTime, memo)


//                viewModel.schedule.observe(viewLifecycleOwner) { result ->
//                    if (result.isNotEmpty()) {
//                        Toast.makeText(requireContext(), "스케줄이 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)
//                    } else {
//                        Toast.makeText(requireContext(), "스케줄 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }

            }
            findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)

            //
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        //
    }

}