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

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getTravelDays(travelId: String, liveData: MutableLiveData<Int>) {
//        travelRef.child(travelId).get()
//            .addOnSuccessListener { snapshot ->
//                val travel = snapshot.getValue(Travel::class.java)
//                if (travel != null) {
//                    val startDate = travel.startDate
//                    val endDate = travel.endDate
//                    if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
//                        try {
//                            val start = LocalDate.parse(startDate) // startDate를 LocalDate로 파싱
//                            val end = LocalDate.parse(endDate) // endDate를 LocalDate로 파싱
//
//                            // 종료일을 포함하는 날짜 차이 계산
//                            val days = ChronoUnit.DAYS.between(start, end.plusDays(1)).toInt()  // 종료일 포함하여 +1일 처리
//                            liveData.value = days // LiveData 업데이트
//                        } catch (e: Exception) {
//                            liveData.value = 0 // 날짜 파싱 에러 처리
//                        }
//                    } else {
//                        liveData.value = 0 // 날짜가 없으면 0
//                    }
//                } else {
//                    liveData.value = 0 // 데이터가 없으면 0
//                }
//            }
//            .addOnFailureListener { exception ->
//                liveData.value = 0 // 실패 시 0
//                println("Error fetching travel days: ${exception.message}")
//            }
//    }



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
    fun postSchedule(newValue: Travel.Schedule, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val scheduleId = scheduleRef.push().key
        if (scheduleId != null) {
            scheduleRef.child(scheduleId).setValue(newValue)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("Schedule ID 생성 실패"))
        }
    }

    // 일정 목록 관찰
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
                println("Error observing schedules: ${error.message}")
            }
        })
    }

    // 위치 정보 업데이트
    fun postLocate(newValue: Travel.Schedule.Locate) {
        locateRef.setValue(newValue)
    }

    // 여행 목록 관찰
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