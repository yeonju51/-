package com.example.tridyday.View

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleBinding
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding

class ScheduleFragment : Fragment() {

    var binding: FragmentScheduleBinding? = null
    val viewModel: ViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = viewModel.retriveId()

        val scheduleAdapter = SchedulesAdapter(viewModel.schedules)
        binding?.recSchedule?.layoutManager = LinearLayoutManager(context)

        if (id != null) {
            viewModel.travels.observe(viewLifecycleOwner) { travels ->
                val travel = travels.find { it.id == id }

                if (travel != null) {
                    binding?.txtScheduleTitle?.text = travel.title

                    val totalDays = if (travel.startDate != null && travel.endDate != null) {
                        viewModel.calculateDaysBetween(travel.startDate!!, travel.endDate!!)
                    } else {
                        0
                    }
                    binding?.buttonContainer?.removeAllViews()

                    for (day in 1..totalDays) {
                        val dayButton = Button(requireContext()).apply {
                            text = "Day $day"
    //                        setOnClickListener { showDaySchedule(day) }
                        }
                        binding?.buttonContainer?.addView(dayButton)
                    }
                }

                id?.let { travelId ->
                    viewModel.observeSchedules(id)
                    viewModel.schedules.observe(viewLifecycleOwner) {
                        binding?.recSchedule?.adapter?.notifyDataSetChanged()
                    }
                }
                binding?.recSchedule?.adapter = scheduleAdapter
            }

            binding?.btnSchedulePlus?.setOnClickListener {
                findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
            }
        }

    }

//    private fun showDaySchedule(day: Int) {
//        val schedulesForDay = schedules.filter { it.day == day }
//        if (schedulesForDay.isNotEmpty()) {
//            val adapter = binding.recSchedule.adapter
//            if (adapter is SchedulesAdapter) {
//                adapter.setSchedules(schedulesForDay)
//            }
//        }
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
