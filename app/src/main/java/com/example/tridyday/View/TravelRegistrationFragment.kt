package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tridyday.View.HomeFragment
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import com.example.tridyday.Model.Travel // Travel 클래스 임포트 경로 수정

import java.util.*

class TravelRegistrationFragment : Fragment() {
    private var _binding: FragmentTravelRegistrationBinding? = null
    private val binding get() = _binding!!

    // 여행 정보를 저장할 변수
    private var travel: Travel? = null

    // Fragment의 view를 생성할 때 호출되는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // FragmentTravelRegistrationBinding을 사용하여 XML 레이아웃을 바인딩
        _binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 생성된 후 호출되는 메서드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 시작일 선택 버튼 클릭 시 날짜 선택 다이얼로그 표시
        binding.startDateButton.setOnClickListener {
            showDatePickerDialog { date ->
                binding.startDateButton.setText(date) // 선택한 날짜를 버튼에 표시
            }
        }

        // 종료일 선택 버튼 클릭 시 날짜 선택 다이얼로그 표시
        binding.endDateButton.setOnClickListener {
            showDatePickerDialog { date ->
                binding.endDateButton.setText(date) // 선택한 날짜를 버튼에 표시
            }
        }

        // 완료 버튼 클릭 시 여행 등록 처리
        binding.btnCompleted.setOnClickListener {
            // 입력된 데이터를 바탕으로 Travel 객체 생성
            val title = binding.txtTitle.text.toString()
            val location = binding.txtLocation.text.toString()
            val startDate = binding.startDateButton.text.toString()
            val endDate = binding.endDateButton.text.toString()

            // 모든 필드가 입력되었는지 확인
            if (title.isNotBlank() && location.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank()) {
                // Travel 객체를 생성하여 여행 정보 저장
                travel = Travel(title, location, startDate, endDate, "여행 배경 이미지 URL") // 실제 배경 이미지 URL 지정 필요

                // 여행 등록 완료 후 부모 Fragment인 HomeFragment로 데이터 전달
                (parentFragment as? HomeFragment)?.addUpcomingTravel(travel!!)

                // 여행 등록 완료 메시지 표시
                Toast.makeText(requireContext(), "여행 등록 완료!", Toast.LENGTH_SHORT).show()

                // 이전 화면으로 돌아가기
                parentFragmentManager.popBackStack()
            } else {
                // 빈 필드가 있을 경우 경고 메시지 표시
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

        // DatePickerDialog를 생성하고 날짜가 선택되면 onDateSelected를 호출
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // 선택된 날짜를 String 형태로 포맷하여 전달
            val date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            onDateSelected(date)
        }, year, month, day).show()
    }

    // Fragment의 view가 파괴될 때 호출되는 메서드
    override fun onDestroyView() {
        super.onDestroyView()
        // 바인딩 객체를 null로 설정하여 메모리 누수 방지
        _binding = null
    }
}
