package com.example.tridyday.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ItemTravelBinding

class TravelAdapter(private val travels: MutableList<Travel>) :
    RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding =
            ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(travels[position])
    }

    override fun getItemCount(): Int = travels.size

    fun updateTravels(newTravels: List<Travel>) {
        travels.clear()
        travels.addAll(newTravels)
        notifyDataSetChanged()
    }

    class TravelViewHolder(private val binding: ItemTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.titleTextView.text = travel.title
            binding.locationTextView.text = travel.location
        }
    }
}
