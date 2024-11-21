package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Schedule
import com.example.tridyday.databinding.ListSchedulesBinding

class SchedulesAdapter(val schedule: Array<Schedule>) : RecyclerView.Adapter<SchedulesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulesAdapter.Holder {
        val binding = ListSchedulesBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: SchedulesAdapter.Holder, position: Int) {
        holder.bind(schedule[position])
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    class Holder(private val binding: ListSchedulesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {

            binding.txtName.text = schedule.title
            binding.txtStartTime.text = schedule.startHour.toString()
            binding.txtEndTime.text = schedule.endHour.toString()

        }
    }
}