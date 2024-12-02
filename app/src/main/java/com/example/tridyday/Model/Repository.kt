package com.example.tridyday.Model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Repository() {
    // FirebaseDatabase 인스턴스를 가져옴
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("user")
    val travelRef = database.getReference("travel")
    val scheduleRef = database.getReference("schedule")
    val locateRef = database.getReference("locate")

    fun observeSchedule(schedule: MutableLiveData<MutableList<Travel.Schedule>>) {
        scheduleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Travel.Schedule>()
                for (data in snapshot.children) {
                    val schedule = data.getValue(Travel.Schedule::class.java)
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

    fun setSchedule(schedule: Travel.Schedule) {
        val scheduleId = scheduleRef.push().key
        scheduleId?.let {
            scheduleRef.child(scheduleId).setValue(schedule)
        }
    }



    fun postLocate(newValue: Travel.Schedule.Locate) {
        userRef.setValue(newValue)
    }

    // 새로운 여행 데이터 저장
    fun saveTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val travelId = travelRef.push().key
        if (travelId != null) {
            travelRef.child(travelId).setValue(travel)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("Travel ID 생성 실패"))
        }
    }

    // Firebase에서 여행 데이터를 실시간으로 관찰
    fun observeTravels(travelList: MutableLiveData<MutableList<Travel>>) {
        travelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val travels = mutableListOf<Travel>()
                for (data in snapshot.children) {
                    val travel = data.getValue(Travel::class.java)
                    if (travel != null) {
                        travels.add(travel)
                    }
                }
                travelList.value = travels // LiveData 업데이트
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })
    }

    // Firebase에서 특정 여행 데이터를 삭제
    fun deleteTravel(travelId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        travelRef.child(travelId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}

