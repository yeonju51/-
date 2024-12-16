package com.example.tridyday.Model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Repository() {
    val database = FirebaseDatabase.getInstance()
    val travelRef = database.getReference("travel")

    private val travelList = MutableLiveData<MutableList<Travel>>()

    // 여행 데이터를 저장할 때 여행 일수도 포함
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val travelId = travelRef.push().key
        if (travelId != null) {
            travel.id = travelId

            // 여행 일수 계산 및 저장
            if (travel.startDate?.isNotEmpty() == true && travel.endDate?.isNotEmpty() == true) {
                try {
                    val start = LocalDate.parse(travel.startDate)
                    val end = LocalDate.parse(travel.endDate)
                    travel.days = ChronoUnit.DAYS.between(start, end).toInt()
                } catch (e: Exception) {
                    travel.days = 0 // 날짜 계산 실패 시 기본값
                }
            }

            travelRef.child(travelId).setValue(travel)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("Travel ID 생성 실패"))
        }
    }

    // 일정 저장
    fun postSchedule(travelId: String, newValue: Travel.Schedule, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        Log.e("SelectedTravel", "Current Travel ID: $travelId")
        val scheduleRef = travelRef.child(travelId).child("schedules")
        val scheduleId = scheduleRef.push().key
        scheduleId?.let {nonNullableIndex ->
            scheduleRef.child(nonNullableIndex).setValue(newValue)
        } ?: run {
            Log.e("Repository", "fail")
        }
    }

    fun observeSchedule(scheduleList: MutableLiveData<MutableList<Travel.Schedule>>) {
        travelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schedules = mutableListOf<Travel.Schedule>()
                for (data in snapshot.children) {
                    try {
                        val schedule = data.getValue(Travel.Schedule::class.java)
                        if (schedule != null) {
                            schedules.add(schedule)
                        } else {
                            println("Failed to parse schedule: ${data.value}")
                        }
                    } catch (e: Exception) {
                        println("Error parsing schedule: ${e.message}")
                    }
                }
                scheduleList.value = schedules
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error observing schedules: ${error.message}")
            }
        })
    }

    // 여행 목록 관찰
    fun observeTravels(travelList: MutableLiveData<MutableList<Travel>>) {
        travelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val travels = mutableListOf<Travel>()
                for (data in snapshot.children) {
                    val travel = data.getValue(Travel::class.java)

                    if (travel != null) {
                        Log.e("확인", "travel 옵저브 ${travel!!.id} ,, ${travel}")
                        travels.add(travel)
                    }
                }
                travelList.value = travels // LiveData 업데이트
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error observing travels: ${error.message}")
            }
        })
    }


    // 여행 삭제
    fun deleteTravel(travelId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        travelRef.child(travelId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
