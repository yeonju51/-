package com.example.tridyday.View

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.R
import com.example.tridyday.databinding.ListSchedulesBinding

class SchedulesAdapter(schedules: LiveData<MutableList<Travel.Schedule>>): RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    private var sortedSchedules: List<Travel.Schedule> = emptyList()

    init {
        schedules.observeForever { scheduleList ->
            sortedSchedules = scheduleList.sortedBy { it.startTime }
            notifyDataSetChanged()
        }
    }

    inner class Holder(private val binding: ListSchedulesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Travel.Schedule?) {
            schedule?.let {
                binding.txtName.text = it.title
                binding.txtTime.text = it.startTime.toString()
                binding.txtStartTime.text = it.startTime.toString()
                binding.txtEndTime.text = it.endTime.toString()

                binding.btnScheduleMemo.setOnClickListener {
                    // 팝업을 띄우는 함수 호출
                    showMemoPopup(schedule)
                }
            }
        }

        private fun showMemoPopup(schedule: Travel.Schedule) {
            if (schedule.memo.isNullOrEmpty()) {
                // 메모가 없으면 알림을 띄움
                Toast.makeText(itemView.context, "메모가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 메모가 있으면 XML 레이아웃을 사용해 팝업을 띄움
                val builder = AlertDialog.Builder(itemView.context)
                val inflater = LayoutInflater.from(itemView.context)
                val dialogView = inflater.inflate(R.layout.dialog_schedule_memo, null)

                // 메모 내용 표시 (TextView를 사용하여 수정 불가)
                val memoTextView = dialogView.findViewById<TextView>(R.id.memoTextView)
                memoTextView.text = schedule.memo

                builder.setView(dialogView)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }

                builder.create().show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSchedulesBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount(): Int = sortedSchedules.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(sortedSchedules.getOrNull(position))
    }

}


