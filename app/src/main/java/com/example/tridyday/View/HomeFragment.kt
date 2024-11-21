package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentHomeBinding
import com.example.tridyday.Model.Travel // Travel 클래스 import

// 이 화면은 여행 리스트를 보여주고, 여행을 추가하거나 선택하는 기능을 제공합니다.
class HomeFragment : Fragment(R.layout.fragment_home) {

    // _binding은 Fragment가 보여지는 동안만 존재하며, Fragment가 파괴되면 null로 설정
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // upcomingTravelList는 여행 정보를 저장하는 리스트
    // 가변리스트를 통해 객체를 추가하거나 삭제하는 것이 가능
    private val upcomingTravelList = mutableListOf<Travel>()

    // onViewCreated 메서드는 Fragment의 UI가 생성된 후 호출되는 메서드입니다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 이 메서드는 Fragment의 뷰가 생성된 후에 호출되며, 부모 클래스에서 정의된 기본 구현을 실행

        // FragmentHomeBinding은 fragment_home.xml 레이아웃 파일과 연결
        _binding = FragmentHomeBinding.bind(view)

        // PlusTrip 버튼이 클릭되면 여행 등록 화면으로 이동
        // findNavController()를 사용하여 navigation을 처리
        binding.PlusTrip.setOnClickListener {
            // 여행 등록 화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_registTripFragment)
        }

        // trip01 버튼이 클릭되면 해당 여행의 세부 스케줄 화면으로 이동합니다.
        // 각 여행 항목을 클릭했을 때 해당 여행의 상세 정보를 보여주는 화면으로 이동할 수 있습니다.
        binding.trip01.setOnClickListener {
            // 여행 스케줄 화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
        }
    }

    fun addUpcomingTravel(travel: Travel) {
        // upcomingTravelList에 새로운 여행 정보를 추가합니다.
        upcomingTravelList.add(travel)

    }

    // Fragment의 view가 파괴될 때 호출, 메모리 누수를 방지합니다.
    override fun onDestroyView() {
        super.onDestroyView()
        // _binding을 null로 설정하여 binding 객체의 참조를 해제
        // Fragment의 view가 더 이상 존재하지 않으므로, binding 객체도 더 이상 필요 없음
        _binding = null
    }
}
