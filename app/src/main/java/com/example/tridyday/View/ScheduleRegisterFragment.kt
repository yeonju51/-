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
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding
import java.time.LocalTime

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register), TimePicker.OnTimeChangedListener {

    private lateinit var binding: FragmentScheduleRegisterBinding
    private val repository = Repository()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.bind(view)

        binding.btnCompleted.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val day = binding.txtDay.text.toString().toIntOrNull()
            val memo = binding.txtMemo.text.toString()

            // 여행 제목이 비어있다면 알림 표시
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "여행 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 몇 번째 날인지가 숫자가 아닌 경우 알림 표시
            else if (day == null) {
                Toast.makeText(requireContext(), "몇째 날인지 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                // 시작 시간과 종료 시간을 LocalTime으로 변환
                val startTime = LocalTime.of(binding.timePickerStart.hour, binding.timePickerStart.minute)
                val endTime = LocalTime.of(binding.timePickerEnd.hour, binding.timePickerEnd.minute)

                // 종료 시간이 시작 시간보다 늦어야 함
                if (endTime.isBefore(startTime) || endTime == startTime) {
                    Toast.makeText(requireContext(), "종료 시간이 시작 시간보다 늦어야 합니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Schedule 객체 생성 및 ViewModel에 전달
                val schedule = Travel.Schedule(
                    title = title,
                    day = day,
                    startTime = startTime,
                    endTime = endTime,
                    memo = memo,
                    locate = Travel.Schedule.Locate(
                        id = "", // 필요한 ID 값을 입력
                        name = "", // 필요한 장소 이름 입력
                        lat = 0.0, // 해당 장소의 위도 입력
                        lng = 0.0, // 해당 장소의 경도 입력
                        place = "" // 장소명 입력
                    )
                )

                // ViewModel에 schedule 객체 전달
                repository.postSchedule(schedule,
                    onSuccess = {
                        Toast.makeText(requireContext(), "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)
                    },
                    onFailure = {
                        Toast.makeText(requireContext(), "일정 추가 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // 일정 등록 후 다른 화면으로 이동
            findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)
        }

    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        //
    }

}