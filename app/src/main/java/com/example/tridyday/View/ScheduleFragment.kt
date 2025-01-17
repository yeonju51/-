package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    var binding: FragmentScheduleBinding? = null
    val viewModel: ViewModel by activityViewModels()

    private var currentDay = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = viewModel.retriveId()

        val scheduleAdapter = SchedulesAdapter(viewModel.schedules)
        binding?.recSchedule?.layoutManager = LinearLayoutManager(context)

        if (id != "") {
            viewModel.travels.observe(viewLifecycleOwner) { travels ->
                val travel = travels.find { it.id == id }

                if (travel != null) {
                    binding?.txtScheduleTitle?.text = travel.title

                    val totalDays = travel.days + 1

                    binding?.buttonContainer?.removeAllViews()

                    viewModel.bringDay(totalDays)

                    for (day in 1..totalDays) {
                        val dayButton = Button(requireContext()).apply {
                            text = "Day $day"

                            setOnClickListener {
                                currentDay = day
                                id?.let { travelId ->
                                    viewModel.observeSchedules(id, currentDay)
                                    viewModel.schedules.observe(viewLifecycleOwner) {
                                        binding?.recSchedule?.adapter?.notifyDataSetChanged()
                                    }
                                }
                                binding?.recSchedule?.adapter = scheduleAdapter
                            }
                        }
                        binding?.buttonContainer?.addView(dayButton)

                        if( day == 1) dayButton.callOnClick()
                    }
                }
            }

            binding?.btnSchedulePlus?.setOnClickListener {
                findNavController().navigate(R.id.action_scheduleFragment_to_scheduleRegisterFragment)
            }

            binding?.btnScheduleBack?.setOnClickListener {
                findNavController().navigate(R.id.action_scheduleFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}