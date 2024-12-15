package com.example.tridyday.Model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Repository() {
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("user")
    val travelRef = database.getReference("travel")
    val scheduleRef = FirebaseDatabase.getInstance().getReference("schedules")
    val locateRef = database.getReference("locate")

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTravelDays(travelId: String, liveData: MutableLiveData<Int>) {
        travelRef.child(travelId).get()
            .addOnSuccessListener { snapshot ->
                val travel = snapshot.getValue(Travel::class.java)
                if (travel != null) {
                    val startDate = travel.startDate
                    val endDate = travel.endDate
                    if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
                        try {
                            val start = LocalDate.parse(startDate) // startDate를 LocalDate로 파싱
                            val end = LocalDate.parse(endDate) // endDate를 LocalDate로 파싱
                            val days = ChronoUnit.DAYS.between(start, end).toInt() // 날짜 차이 계산
                            liveData.value = days // LiveData 업데이트
                        } catch (e: Exception) {
                            liveData.value = 0 // 날짜 파싱 에러 처리
                        }
                    } else {
                        liveData.value = 0 // 날짜가 없으면 0
                    }
                } else {
                    liveData.value = 0 // 데이터가 없으면 0
                }
            }
            .addOnFailureListener { exception ->
                liveData.value = 0 // 실패 시 0
                println("Error fetching travel days: ${exception.message}")
            }
    }

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
        locateRef.setValue(newValue)
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

