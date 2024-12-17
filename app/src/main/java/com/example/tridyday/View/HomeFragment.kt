package com.example.tridyday.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.ViewModel.ViewModel
import com.example.tridyday.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.adventureRecyclerView?.layoutManager = LinearLayoutManager(context)

        viewModel.travels.observe(viewLifecycleOwner) {
            binding?.adventureRecyclerView?.adapter?.notifyDataSetChanged()
        }

        val adapter = TravelAdapter(viewModel.travels)
        adapter.setOnClickListener(object: TravelAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, id: String) {
                viewModel.bringId(id)
                findNavController().navigate(R.id.action_homeFragment_to_scheduleFragment)
            }
        })

        binding?.adventureRecyclerView?.adapter = adapter

        // Add 버튼 클릭 시 TravelRegistrationFragment로 이동
        binding?.addButton?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_travelRegistrationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun formatDate(date: String?): String {
        if (date.isNullOrEmpty()) return ""
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate ?: date)
        } catch (e: Exception) {
            date ?: ""
        }
    }
}
