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

    fun observeSchedule(schedule: MutableLiveData<String>) {
        userRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                schedule.postValue( snapshot.value.toString() )
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postSchedule(newValue: String) {
        userRef.setValue(newValue)
    }
}