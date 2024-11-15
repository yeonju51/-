// TravelRegistrationFragment.kt
package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentTravelRegistrationBinding
import java.util.*

class TravelRegistrationFragment : Fragment() {
    private var _binding: FragmentTravelRegistrationBinding? = null
    private val binding get() = _binding!!

    // 여행 정보를 저장할 변수
    private var travel: Travel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 시작일 선택 버튼
        binding.startDateButton.setOnClickListener {
            showDatePickerDialog { date ->
                binding.startDateButton.setText(date)
            }
        }

        // 종료일 선택 버튼
        binding.endDateButton.setOnClickListener {
            showDatePickerDialog { date ->
                binding.endDateButton.setText(date)
            }
        }

        // 완료 버튼
        binding.completeButton.setOnClickListener {
            // 입력된 데이터를 바탕으로 Travel 객체 생성
            val title = binding.titleInput.text.toString()
            val location = binding.locationInput.text.toString()
            val startDate = binding.startDateButton.text.toString()
            val endDate = binding.endDateButton.text.toString()

            if (title.isNotBlank() && location.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank()) {
                // 여행 정보 저장
                travel = Travel(title, location, startDate, endDate, "여행 배경 이미지 URL") // 실제 배경 이미지 URL 지정 필요

                // 여행 등록 완료 후 MainActivity로 데이터 전달
                (activity as? MainActivity)?.addUpcomingTravel(travel!!)

                // 여행 등록 완료 메시지
                Toast.makeText(requireContext(), "여행 등록 완료!", Toast.LENGTH_SHORT).show()

                // 홈 화면으로 돌아가기
                parentFragmentManager.popBackStack()
            } else {
                // 빈 입력값이 있을 경우 경고 메시지
                Toast.makeText(requireContext(), "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // DatePickerDialog를 보여주는 함수
    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // 선택된 날짜를 String으로 포맷하여 onDateSelected 호출
            val date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            onDateSelected(date)
        }, year, month, day).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
