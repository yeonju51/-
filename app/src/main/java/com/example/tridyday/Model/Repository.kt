package com.example.tridyday.Model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Repository() {
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("user")
    val travelRef = database.getReference("travel")
    val scheduleRef = FirebaseDatabase.getInstance().getReference("schedules")
    val locateRef = database.getReference("locate")

    fun postSchedule(newValue: Travel.Schedule, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val scheduleId = scheduleRef.push().key
        if (scheduleId != null) {
            travelRef.child("schedules").child(scheduleId).setValue(newValue)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("Schedule ID 생성 실패"))
        }
    }

    // Schedule 데이터 실시간 관찰
    fun observeSchedule(scheduleList: MutableLiveData<MutableList<Travel.Schedule>>) {
        scheduleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Travel.Schedule>()
                for (data in snapshot.children) {
                    val schedule = data.getValue(Travel.Schedule::class.java)
                    if (schedule != null) {
                        schedules.add(schedule)
                    }
                }
                scheduleList.value = schedules // LiveData 업데이트
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리 (로그 출력 또는 UI 알림)
                println("Error observing schedules: ${error.message}")
            }
        })
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
        // addOnFailureListener는 삭제가 실패했을 때 호출되는 리스너
        // 삭제 작업이 실패하면, 이 리스너 내에서 onFailure(it)가 호출, it은 실패한 원인, 즉 예외를 나타냄
    }
}

