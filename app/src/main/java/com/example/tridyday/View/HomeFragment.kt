package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // 여행을 선택했을 때 스케줄 fragment로 이동
        binding.trip01.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
        }

        // 여행 추가 버튼 클릭 시 등록 fragment로 이동
        binding.PlusTrip.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registTripFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// MainActivity.kt
//package com.example.myapplication
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.tridyday.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val travelList = mutableListOf<Travel>() // 여행 정보를 저장할 리스트
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 초기 데이터 (기본 여행 리스트)
//        travelList.addAll(
//            listOf(
//                Travel(
//                    title = "단양 여행",
//                    location = "단양",
//                    startDate = "2024-11-28",
//                    endDate = "2024-11-30",
//                    imageUrl = "https://example.com/yourimage1.jpg"
//                ),
//                Travel(
//                    title = "결혼기념일 여행",
//                    location = "가평",
//                    startDate = "2024-12-08",
//                    endDate = "2024-12-10",
//                    imageUrl = "https://example.com/yourimage2.jpg"
//                )
//            )
//        )
//
//        // Set up RecyclerView
//        val travelAdapter = TravelAdapter(travelList)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = travelAdapter
//    }
//
//    // 여행 정보를 추가하는 메서드
//    fun addUpcomingTravel(travel: Travel) {
//        travelList.add(travel)
//        // RecyclerView의 어댑터에 변경사항을 알리고 화면을 갱신
//        (binding.recyclerView.adapter as TravelAdapter).notifyItemInserted(travelList.size - 1)
//    }
//}