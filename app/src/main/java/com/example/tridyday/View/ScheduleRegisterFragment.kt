package com.example.tridyday.View

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tridyday.Model.Plan
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentScheduleRegisterBinding

class ScheduleRegisterFragment : Fragment(R.layout.fragment_schedule_register) {

    private lateinit var binding: FragmentScheduleRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleRegisterBinding.bind(view)

        binding.btnCompleted.setOnClickListener {
            val titleText = binding.txtTitle.text.toString()
            val timeText = binding.txtTime.text.toString()
            val contentText = binding.txtContent.text.toString()

            val plan = Plan(title = titleText, time = timeText, contents = contentText)

            Toast.makeText(requireContext(), "Plan이 생성되었습니다: $plan", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleRegisterFragment.
         */
        // TODO: Rename and change types and number of parameters

    }
}