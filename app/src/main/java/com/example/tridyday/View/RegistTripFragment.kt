package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import com.example.tridyday.View.HomeFragment

// 여행 등록 화면을 담당하는 Fragment 클래스
class RegistTripFragment : Fragment() {

    // ViewBinding 객체 선언 (FragmentTravelRegistrationBinding 사용)
    private var binding: FragmentTravelRegistrationBinding? = null

    // onCreateView: Fragment의 뷰를 생성하는 부분
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding 초기화 (FragmentTravelRegistrationBinding 객체 생성)
        binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // onViewCreated: 뷰가 생성되고 나서 호출되는 메서드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "완료" 버튼 클릭 리스너 설정
        binding?.btnCompleted?.setOnClickListener {
            // HomeFragment로 화면 전환
            parentFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
                addToBackStack(null) // 뒤로가기 가능하도록 설정
            }
        }
    }

    // onDestroyView: Fragment의 뷰가 파괴될 때 호출되는 메서드
    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding 객체를 null로 설정하여 메모리 누수를 방지
        binding = null
    }
}
