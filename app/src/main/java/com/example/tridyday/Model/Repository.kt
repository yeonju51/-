package com.example.tridyday.Model

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Repository {
    // FirebaseDatabase 인스턴스를 가져옴
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("user")

    fun saveSchedule(schedule: Schedule) {
        val scheduleId = database.getReference("schedules").push().key
        if (scheduleId != null) {
            database.getReference("schedules").child(scheduleId).setValue(schedule)
        }
    }

    fun getSchedules(onSchedulesReceived: (List<Schedule>) -> Unit) {
        database.getReference("schedules").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Schedule>()
                for (data in snapshot.children) {
                    val schedule = data.getValue(Schedule::class.java)
                    if (schedule != null) {
                        schedules.add(schedule)
                    }
                }
                onSchedulesReceived(schedules)
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 처리
            }
        })
    }
}
