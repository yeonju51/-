package com.example.tridyday.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tridyday.Model.Travel
import com.example.tridyday.R

class TravelRegistrationFragment : Fragment(R.layout.fragment_travel_registration) {

    private lateinit var travelTitle: EditText
    private lateinit var travelLocation: EditText
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var photoAttachmentButton: Button
    private lateinit var btnCompleted: Button
    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        travelTitle = view.findViewById(R.id.travel_title)
        travelLocation = view.findViewById(R.id.travel_location)
        startDateButton = view.findViewById(R.id.start_date_button)
        endDateButton = view.findViewById(R.id.end_date_button)
        photoAttachmentButton = view.findViewById(R.id.photo_attachment_button)
        btnCompleted = view.findViewById(R.id.btnCompleted)

        btnCompleted.setOnClickListener {
            val travel = Travel(
                title = travelTitle.text.toString(),
                location = travelLocation.text.toString(),
                startDate = startDateButton.text.toString(),
                endDate = endDateButton.text.toString()
            )
            val bundle = Bundle().apply { putParcelable("newTravel", travel) }
            findNavController().navigate(R.id.action_travelRegistrationFragment_to_homeFragment, bundle)
        }
    }
}
