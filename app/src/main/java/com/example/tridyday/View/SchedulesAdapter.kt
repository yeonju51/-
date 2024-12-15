package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ListSchedulesBinding

class SchedulesAdapter : RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    private var scheduleList = mutableListOf<Travel.Schedule>()

    fun setSchedules(schedules: List<Travel.Schedule>) {
        scheduleList.clear()
        scheduleList.addAll(schedules)
        notifyDataSetChanged()  // 데이터 변경 후 RecyclerView에 알림
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSchedulesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(scheduleList[position])
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    class Holder(private val binding: ListSchedulesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: Travel.Schedule) {
            binding.apply {
                txtName.text = schedule.title
                txtStartTime.text = schedule.startTime.toString()
                txtEndTime.text = schedule.endTime.toString()
            }
        }
    }
}
