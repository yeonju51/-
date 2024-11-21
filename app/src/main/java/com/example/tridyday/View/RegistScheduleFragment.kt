package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding


class RegistScheduleFragment : Fragment() {

    var binding: FragmentScheduleRegisterBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScheduleRegisterBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnCompleted?.setOnClickListener {    // 일정 등록 버튼
            findNavController().navigate(R.id.action_scheduleRegisterFragment_to_scheduleFragment)
        }

        binding?.btnRecommendSpot?.setOnClickListener {    // 추천 스팟 버튼
            findNavController().navigate(R.id.action_scheduleRegisterFragment_to_recommendFragment)
        }

        binding?.btnRegisterMap?.setOnClickListener {    // 위치 버튼
            findNavController().navigate(R.id.action_scheduleRegisterFragment_to_registMapFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}