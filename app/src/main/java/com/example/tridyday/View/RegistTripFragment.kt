package com.example.tridyday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import com.example.tridyday.Model.Travel

import android.app.DatePickerDialog
import android.widget.DatePicker
import java.util.*

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import android.app.Activity

class RegistTripFragment : Fragment() {

    private var binding: FragmentTravelRegistrationBinding? = null
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 시작 날짜 버튼 클릭 리스너
        binding?.startDateButton?.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                startDate = date
                binding?.startDateButton?.text = date
            }
        }

        // 종료 날짜 버튼 클릭 리스너
        binding?.endDateButton?.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                endDate = date
                binding?.endDateButton?.text = date
            }
        }

        // 완료 버튼 클릭 리스너
        binding?.btnCompleted?.setOnClickListener {
            val newTravel = Travel(
                title = binding?.travelTitle?.text.toString(),
                location = binding?.travelLocation?.text.toString(),
                startDate = startDate ?: "",  // 선택된 시작 날짜
                endDate = endDate ?: ""       // 선택된 종료 날짜
            )
            findNavController().previousBackStackEntry?.arguments?.putParcelable(
                "newTravel",
                newTravel
            )
            findNavController().popBackStack()
        }
    }

    private fun showDatePickerDialog(onDateSet: (Int, Int, Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                onDateSet(selectedYear, selectedMonth, selectedDay)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    class RegistTripFragment : Fragment() {

        private var binding: FragmentTravelRegistrationBinding? = null
        private val PICK_IMAGE_REQUEST = 1

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentTravelRegistrationBinding.inflate(inflater, container, false)
            return binding?.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // 사진 첨부 버튼 클릭 리스너
            binding?.photoAttachmentButton?.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }

            // 완료 버튼 클릭 리스너
            binding?.btnCompleted?.setOnClickListener {
                val newTravel = Travel(
                    title = binding?.travelTitle?.text.toString(),
                    location = binding?.travelLocation?.text.toString(),
                    startDate = binding?.startDateButton?.text.toString(),
                    endDate = binding?.endDateButton?.text.toString()
                )
                findNavController().previousBackStackEntry?.arguments?.putParcelable(
                    "newTravel",
                    newTravel
                )
                findNavController().popBackStack()
            }
        }

       /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null) {
                val imageUri = data.data
                // 사진 URI 처리 로직
                Toast.makeText(requireContext(), "사진 선택됨: $imageUri", Toast.LENGTH_SHORT).show()
            }

        }
*/

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }
    }
}
