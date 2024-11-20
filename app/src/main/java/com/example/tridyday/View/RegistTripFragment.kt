package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tridyday.R.id.action_registTripFragment_to_homeFragment
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding


class RegistTripFragment : Fragment() {

    var binding: FragmentTravelRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTravelRegistrationBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnCompleted?.setOnClickListener{    // 여행 등록하면 홈으로 돌아감
            findNavController().navigate(action_registTripFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}