package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ListSchedulesBinding

class SchedulesAdapter(private var schedules: MutableList<Travel.Schedule>,): RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    fun setSchedules(newSchedules: List<Travel.Schedule>) {
        schedules = newSchedules.toMutableList()  // 새로운 데이터로 리스트 갱신
        notifyDataSetChanged()  // RecyclerView에 데이터 변경 알림
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSchedulesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    class Holder(private val binding: ListSchedulesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: Travel.Schedule) {
            binding.apply {
                txtName.text = schedule.title
                txtTime.text = schedule.startTime.toString()
                txtStartTime.text = schedule.startTime.toString()
                txtEndTime.text = schedule.endTime.toString()
            }
        }
    }
}
