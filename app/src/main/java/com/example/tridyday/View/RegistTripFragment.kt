// RegistTripFragment는 여행 등록 화면을 담당하는 Fragment
// 이 Fragment는 사용자가 "완료" 버튼을 클릭하면 HomeFragment로 화면을 전환하는 기능을 제공

package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import com.example.tridyday.View.HomeFragment

// 여행 등록 화면을 담당하는 Fragment 클래스
class RegistTripFragment : Fragment() {

    // ViewBinding 객체 선언 (FragmentTravelRegistrationBinding 사용)
    var binding: FragmentTravelRegistrationBinding? = null

    // onCreateView: Fragment의 뷰를 생성하는 부분
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding 초기화 (FragmentTravelRegistrationBinding 객체 생성)
        binding = FragmentTravelRegistrationBinding.inflate(inflater)

        // Fragment의 뷰를 반환 (binding?.root는 실제 화면에 표시될 뷰)
        return binding?.root
    }

    // onViewCreated: 뷰가 생성되고 나서 호출되는 메서드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "완료" 버튼 클릭 리스너 설정
        binding?.btnCompleted?.setOnClickListener {
            // 버튼이 클릭되었을 때 실행될 코드

            // HomeFragment 인스턴스를 생성 (홈 화면으로 이동할 Fragment)
            val homeFragment = HomeFragment()

            // FragmentTransaction을 사용하여 Fragment를 교체
            requireActivity().supportFragmentManager.beginTransaction()
                // 현재 Activity에서 frgNav ID를 가진 FragmentContainerView에 HomeFragment를 교체
                .replace(R.id.frgNav, homeFragment) // ID를 fragment_container에서 frgNav로 변경
                // 이전 화면으로 돌아갈 수 있도록 백스택에 추가
                .addToBackStack(null)
                // 트랜잭션 실행
                .commit()
        }
    }

    // onDestroyView: Fragment의 뷰가 파괴될 때 호출되는 메서드
    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding 객체를 null로 설정하여 메모리 누수를 방지
        binding = null
    }
}
