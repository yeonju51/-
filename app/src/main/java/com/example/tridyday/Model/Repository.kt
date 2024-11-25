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

    fun observeSchedule(schedule: MutableLiveData<MutableList<Schedule>>) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scheduleList = mutableListOf<Schedule>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(Schedule::class.java)
                    if (item != null) {
                        scheduleList.add(item)
                    }
                }
                schedule.postValue(scheduleList)
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })
    }


    fun postSchedule(newValue: String) {
        userRef.setValue(newValue)
    }
}