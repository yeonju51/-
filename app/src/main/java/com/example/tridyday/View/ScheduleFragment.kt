package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tridyday.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment 레이아웃을 결합
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // 여행의 일수에 따라 Day 버튼 동적으로 추가
        val totalDays = 5 // 일단 5일 설정
        for (day in 1..totalDays) {
            val dayButton = Button(requireContext()).apply {
                text = "Day $day"
                setOnClickListener {
                    // Day 버튼 클릭 시 동작 설정
                }
            }
            binding.buttonContainer.addView(dayButton)
        }

        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
        binding.btnSchedulePlus.setOnClickListener {
            replaceFragment(ScheduleRegisterFragment())
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().run {
            replace(binding.frmFrag.id, fragment)
            commit()
        }
    }
}


//package com.example.tridyday.View
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.LinearLayout
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.example.tridyday.R
//import com.example.tridyday.databinding.ActivityTripScheduleBinding
//
//class TripScheduleActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityTripScheduleBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_schedule)
//
//        val buttonContainer = findViewById<LinearLayout>(R.id.buttonContainer)
//
//        // 여행의 일수에 따라 Day 버튼 동적으로 추가
//        val totalDays = 5 // 일단 5일 설정
//        for (day in 1..totalDays) {
//            val dayButton = Button(this).apply {
//                text = "Day $day"
//                setOnClickListener {
//                    // Day 버튼 클릭 시 동작 설정
//                }
//            }
//            buttonContainer.addView(dayButton)
//        }
//
//        fun replaceFragment(fragment: Fragment) {
//            supportFragmentManager.beginTransaction().run {
//                binding = ActivityTripScheduleBinding.inflate(layoutInflater)
//                replace(binding.frmFrag.id, fragment)
//                commit()
//            }
//        }
//
//        // + 버튼 클릭 시 ScheduleRegisterFragment로 이동
//        val btnSchedulePlus = findViewById<Button>(R.id.btn_schedule_plus)
//        btnSchedulePlus.setOnClickListener {
//            replaceFragment(ScheduleRegisterFragment())
//        }
//    }
//}
