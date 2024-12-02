package com.example.tridyday.Model

import androidx.lifecycle.MutableLiveData
import com.example.tridyday.ViewModel.locateClass
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



    fun observeLocate(locate: MutableLiveData<locateClass>) {
        userRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                locate.postValue(snapshot.value as locateClass?)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postLocate(newValue: locateClass) {
        userRef.setValue(newValue)
    }
}
