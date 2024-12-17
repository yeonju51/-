package com.example.tridyday.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Repository {

    private val database = FirebaseDatabase.getInstance()
    private val travelRef = database.getReference("travel")

    // 여행 데이터 파베에 저장
    fun saveTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val travelId = travelRef.push().key
        if (travelId != null) {
            travel.id = travelId

            if (travel.startDate?.isNotEmpty() == true && travel.endDate?.isNotEmpty() == true) {
                try {
                    val start = LocalDate.parse(travel.startDate)
                    val end = LocalDate.parse(travel.endDate)
                    travel.days = ChronoUnit.DAYS.between(start, end).toInt() + 1 // +1은 시작일부터 포함
                } catch (e: Exception) {
                    travel.days = 0
                }
            }

            // 파베에 여행 데이터 저장
            travelRef.child(travelId).setValue(travel)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("Travel ID 생성 실패"))
        }
    }


    // 파베에서 여행 받아오기
    fun observeTravels(travelList: MutableLiveData<MutableList<Travel>>) {
        travelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val travels = mutableListOf<Travel>()
                for (data in snapshot.children) {
                    val travel = data.getValue(Travel::class.java)
                    if (travel != null) {
                        travels.add(travel) // Travel 객체로 변환하고 리스트에 추가
                    }
                }
                travelList.value = travels
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error observing travels: ${error.message}")
            }
        })
    }

    fun postSchedule(travelId: String, newValue: Travel.Schedule) {
        val scheduleRef = travelRef.child(travelId).child("schedules")
        val scheduleId = scheduleRef.push().key
        scheduleId?.let { nonNullableIndex ->
            scheduleRef.child(nonNullableIndex).setValue(newValue)
        } ?: run {
            Log.e("Repository", "fail")
        }
    }

    fun observeSchedule(
        travelId: String,
        scheduleList: MutableLiveData<MutableList<Travel.Schedule>>,
        filter: (Travel.Schedule) -> Boolean = { true }
    ) {
        val scheduleRef = travelRef.child(travelId).child("schedules")
        scheduleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Travel.Schedule>()
                for (data in snapshot.children) {
                    val schedule = data.getValue(Travel.Schedule::class.java)
                    if (schedule != null && filter(schedule)) { // 필터 적용
                        schedules.add(schedule)
                    } else if (schedule == null) {
                        println("Failed to parse schedule: ${data.value}")
                    }
                    scheduleList.value = schedules
                }
            }
            override fun onCancelled(error: DatabaseError) {
                println("Error observing schedules: ${error.message}")
            }
        })
    }

}
