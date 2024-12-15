package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ItemTravelBinding

class TravelAdapter(
    private var travels: MutableList<Travel>,
    private val onClick: (Travel) -> Unit
) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    inner class TravelViewHolder(private val binding: ItemTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.apply {
                // 제목과 위치 설정
                titleTextView.text = travel.title
                locationTextView.text = travel.location

                // 날짜 설정 (시작일 ~ 종료일)
                datesTextView.text = "${travel.startDate} ~ ${travel.endDate}"

                // 클릭 이벤트 설정
                root.setOnClickListener { onClick(travel) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
}
