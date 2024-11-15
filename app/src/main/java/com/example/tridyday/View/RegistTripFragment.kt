package com.example.tridy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tridy.databinding.FragmentHomeBinding
import com.example.tridy.databinding.FragmentRegistTripBinding
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentRegistTripBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class RegistTripFragment : Fragment() {
    // TODO: Rename and change types of parameters


    var binding: FragmentRegistTripBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegistTripBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tripSet?.setOnClickListener{    // 여행 등록하면 홈으로 돌아감
            findNavController().navigate(R.id.action_registTripFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}