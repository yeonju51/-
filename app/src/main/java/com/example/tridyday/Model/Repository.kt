package com.example.tridyday.Model

import androidx.lifecycle.MutableLiveData
import com.example.tridyday.ViewModel.locateClass
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context

class Repository(private val context: Context) {
    // FirebaseDatabase 인스턴스를 가져옴
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("user")
    val travelRef = database.getReference("travel")
    val scheduleRef = database.getReference("schedule")

    fun observeSchedule(schedule: MutableLiveData<MutableList<Schedule>>) {
        scheduleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Schedule>()
                for (data in snapshot.children) {
                    val schedule = data.getValue(Schedule::class.java)
                    if (schedule != null) {
                        schedules.add(schedule)
                    }
                }
                schedule.value = schedules
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("hmm..")
            }
        })
    }

    fun saveSchedule(schedule: Schedule) {
        val scheduleId = scheduleRef.push().key
        if (scheduleId != null) {
            scheduleRef.child(scheduleId).setValue(schedule)
        }
    }

    fun getSchedules(onSchedulesReceived: (List<Schedule>) -> Unit) {
        scheduleRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
                TODO("ahah")
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
