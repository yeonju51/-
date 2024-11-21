package com.example.tridyday.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tridyday.databinding.FragmentRegistMapBinding
import com.google.android.gms.maps.MapView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegistMapFragment : Fragment() {
    // TODO: Rename and change types of parameters

    var binding: FragmentRegistMapBinding?= null


    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistMapBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val searchBar = binding?.searchBar


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}
