package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.databinding.ItemTravelBinding
import com.example.tridyday.Model.Travel



class TravelAdapter(private val travels: List<Travel>) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        val travel = travels[position]
        holder.bind(travel)
    }

    override fun getItemCount(): Int = travels.size

    inner class TravelViewHolder(private val binding: ItemTravelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.titleTextView.text = travel.title
            binding.locationTextView.text = travel.location
            binding.datesTextView.text = "${travel.startDate} ~ ${travel.endDate}"
        }
    }
}
