package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ItemTravelBinding

class TravelAdapter(
    val travels: LiveData<MutableList<Travel>>
) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    inner class TravelViewHolder(private val binding: ItemTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel?) {
            travel?.let {
                binding.titleTextView.text = it.title
                binding.locationTextView.text = it.location
                binding.datesTextView.text = "${it.startDate} ~ ${it.endDate}"

                val id = travel.id

                binding.root.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION && itemClickListener != null) {
                        itemClickListener.onItemClick(itemView, pos, id)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, id: String)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(travels.value?.getOrNull(position))
    }

    override fun getItemCount(): Int = travels.value?.size ?: 0

//    fun updateTravels(newTravels: List<Travel>) {
//        travels.clear()
//        travels.addAll(newTravels)
//        notifyDataSetChanged()
//    }
}
