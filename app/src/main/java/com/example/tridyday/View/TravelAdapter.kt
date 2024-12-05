package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ItemTravelBinding

class TravelAdapter(private val travels: MutableList<Travel>) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    //  ViewHolder(화면에 보이는 리스트 아이템)를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelViewHolder(binding)
    }

    // onBindViewHolder는 각 아이템을 화면에 표시할 때 호출
    //position은 현재 항목이 리스트에서 몇 번째 항목인지 나타냄
    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(travels[position])
    }

    // 0이면 RecyclerView는 빈 화면으로 보이고, 데이터가 추가되면 아이템 개수만큼 화면에 표시
    override fun getItemCount(): Int = travels.size

    class TravelViewHolder(private val binding: ItemTravelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            binding.titleTextView.text = travel.title
            binding.locationTextView.text = travel.location
            binding.datesTextView.text = "${travel.startDate} ~ ${travel.endDate}"
        }
    }

    // 새로운 여행 데이터(newTravels)
    fun updateTravels(newTravels: List<Travel>) {
        travels.clear()
        travels.addAll(newTravels)
        notifyDataSetChanged()  // RecyclerView에 데이터 변경 알림, RecyclerView는 데이터 변경을 자동으로 인지하지 못함
    }
}

