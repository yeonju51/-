package com.example.tridyday.Model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Repository {
    val database = Firebase.database
    val userRef = database.getReference("user")

    fun saveSchedule(schedule: Schedule) {
        val scheduleId = database.child("schedules").push().key
        if (scheduleId != null) {
            database.child("schedules").child(scheduleId).setValue(schedule)
        }
    }

    fun getSchedules(onSchedulesReceived: (List<Schedule>) -> Unit) {
        database.child("schedules").addListenerForSingleValueEvent(object : ValueEventListener {
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