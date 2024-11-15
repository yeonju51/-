package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTravelBinding

class TravelAdapter(private val travels: List<Travel>) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        val travel = travels[position]
        holder.bind(travel)
    }

    override fun getItemCount(): Int {
        return travels.size
    }

    inner class TravelViewHolder(private val binding: ItemTravelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.titleTextView.text = travel.title
            binding.locationTextView.text = travel.location
            binding.startDateTextView.text = travel.startDate
            binding.endDateTextView.text = travel.endDate
            // 여행 이미지도 추가할 수 있습니다. 예: Picasso 라이브러리 사용
        }
    }
}
