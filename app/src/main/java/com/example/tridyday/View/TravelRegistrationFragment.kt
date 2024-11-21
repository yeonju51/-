package com.example.tridyday.View

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tridyday.Model.Travel  // Travel 클래스 임포트
import com.example.tridyday.R
import com.example.tridyday.databinding.FragmentTravelRegistrationBinding
import java.util.*

class TravelRegistrationFragment : Fragment(R.layout.fragment_travel_registration) {

    private var _binding: FragmentTravelRegistrationBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelRegistrationBinding.bind(view)

        // Date Picker
        binding.startDateButton.setOnClickListener { showDatePicker(binding.startDateButton) }
        binding.endDateButton.setOnClickListener { showDatePicker(binding.endDateButton) }

        // Photo Picker
        binding.photoAttachmentButton.setOnClickListener { pickImageFromGallery() }

        // Complete Button
        binding.completeRegistrationButton.setOnClickListener {
            val travel = Travel(
                title = binding.travelTitle.text.toString(),
                startDate = binding.startDateButton.text.toString(),
                endDate = binding.endDateButton.text.toString(),
                // selectedImageUri가 null일 경우 빈 문자열 처리
                photoUri = selectedImageUri?.toString() ?: ""
            )
            (parentFragment as? HomeFragment)?.addTravel(travel)
            parentFragmentManager.popBackStack()
        }
    }

    private fun showDatePicker(button: View) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                (button as? Button)?.text = "$year-${month + 1}-$day"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.photoAttachmentButton.text = "사진 선택됨"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
