package com.example.tridyday.view

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import java.util.*

class TravelRegistrationFragment : Fragment(R.layout.fragment_travel_registration) {

    private lateinit var binding: FragmentTravelRegistrationBinding
    private var photoUri: Uri? = null
    private var startDate: String? = null
    private var endDate: String? = null

    private val viewModel: ViewModel by viewModels()

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                photoUri = result.data?.data
                Toast.makeText(requireContext(), "사진 선택됨", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTravelRegistrationBinding.bind(view)

        binding.startDateButton.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                startDate = date
                binding.startDateButton.text = date
            }
        }

        binding.endDateButton.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                endDate = date
                binding.endDateButton.text = date
            }
        }

        binding.photoAttachmentButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            selectImageLauncher.launch(intent)
        }


        binding.btnCompleted.setOnClickListener {
            val title = binding.travelTitle.text.toString()
            val location = binding.travelLocation.text.toString()

            if (title.isNotEmpty() && location.isNotEmpty() && startDate != null && endDate != null) {
                val travel = Travel(
                    title = title,
                    location = location,
                    startDate = startDate,
                    endDate = endDate,
                    photoUri = photoUri.toString()
                )

                viewModel.addTravel(travel, onSuccess = {
                    Toast.makeText(requireContext(), "여행 등록 성공", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_travelRegistrationFragment_to_homeFragment)
                }, onFailure = {
                    Toast.makeText(requireContext(), "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                })
            } else {
                Toast.makeText(requireContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog(onDateSet: (Int, Int, Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            onDateSet(selectedYear, selectedMonth, selectedDay)
        }, year, month, day).show()
    }
}
