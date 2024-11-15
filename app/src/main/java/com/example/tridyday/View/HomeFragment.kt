package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Travel
import com.example.myapplication.TravelAdapter
import com.example.tridy.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    var binding: FragmentHomeBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.trip01?.setOnClickListener{    // 여행 누르면 그 여행으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
        }

        binding?.PlusTrip?.setOnClickListener {  // 여행 추가하는 버튼 연결
            findNavController().navigate(R.id.action_homeFragment_to_registTripFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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