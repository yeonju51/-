package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import com.example.tridyday.Model.Travel


class RegistTripFragment : Fragment() {

    private var binding: FragmentTravelRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnCompleted?.setOnClickListener {
            val newTravel = Travel(
                title = binding?.travelTitle?.text.toString(),
                location = binding?.travelLocation?.text.toString(),
                startDate = binding?.startDateButton?.text.toString(),
                endDate = binding?.endDateButton?.text.toString()
            )
            findNavController().previousBackStackEntry?.arguments?.putParcelable("newTravel", newTravel)
            findNavController().popBackStack()
                Bundle().apply { putParcelable("newTravel", newTravel) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
