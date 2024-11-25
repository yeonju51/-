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

    // TravelViewHolder 클래스는 RecyclerView 항목에서 Travel 객체의 데이터를 보여줌
    //bind 메서드를 통해 각 Travel 객체의 정보를 해당 항목의 텍스트 뷰에 바인딩
    inner class TravelViewHolder(private val binding: ItemTravelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.titleTextView.text = travel.title
            binding.locationTextView.text = travel.location
            // 예시: "2024-11-01 ~ 2024-11-10"
            binding.datesTextView.text = "${travel.startDate} ~ ${travel.endDate}"
        }
    }
}
