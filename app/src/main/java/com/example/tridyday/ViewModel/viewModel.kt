package com.example.tridyday.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

val UNLOCATE = Travel.Schedule.Locate("", "", 0.0, 0.0, "")

class ViewModel : ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<MutableList<Travel>>()
    val travels: LiveData<MutableList<Travel>> get() = _travels

    private val _schedules = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedules: LiveData<MutableList<Travel.Schedule>> get() = _schedules

    init {
        repository.observeSchedule(_schedules)
        repository.observeTravels(_travels)
    }

    private val _locate = MutableLiveData<Travel.Schedule.Locate>(UNLOCATE)
    val locate: LiveData<Travel.Schedule.Locate> get() = _locate

    fun setSchedule(schedule: Travel.Schedule) {
        _schedules.value?.add(schedule)  // 새로운 일정을 추가
        _schedules.value = _schedules.value  // LiveData를 갱신
    }

    val travelDaysLiveData = MutableLiveData<Int>()

    fun addSchedule(schedule: Travel.Schedule) {
        // Repository를 통해 Firebase에 스케줄 저장
        repository.postSchedule(schedule, onSuccess = {
            // 성공하면 _schedules 업데이트
            _schedules.value?.add(schedule)
            _schedules.value = _schedules.value // LiveData 갱신
        }, onFailure = {
            Log.e("ScheduleViewModel", "스케줄 등록 실패: ${it.message}")
        })
    }

    fun observeTravels() {
        repository.observeTravels(_travels) // Repository에서 데이터를 가져와서 LiveData 업데이트
    }

    // 여행 데이터를 추가할 때 여행 일수를 계산하고 저장
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.saveTravel(travel, {
            // 성공 시 _travels 업데이트
            val currentTravels = _travels.value?.toMutableList() ?: mutableListOf()
            currentTravels.add(travel)
            _travels.value = currentTravels
            onSuccess()
        }, onFailure)
    }

    fun setLocate(
        newName: String,
        newID: String,
        newLat: Double,
        newLng: Double,
        newPlace: String
    ) {
        val newLocate = _locate.value?.let {
            it.name = newName
            it.id = newID
            it.lat = newLat
            it.lng = newLng
            it.place = newPlace
            _locate.value
        } ?: UNLOCATE
        repository.postLocate(newLocate)
    }

    // startDate, endDate를 저장할 MutableLiveData
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate

    // 날짜를 ViewModel에서 처리하기 위해 startDate 값을 설정
    fun setStartDate(date: String) {
        _startDate.value = date
    }

    // 날짜를 ViewModel에서 처리하기 위해 endDate 값을 설정
    fun setEndDate(date: String) {
        _endDate.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateTravelDays() {
        val start = _startDate.value?.let { LocalDate.parse(it) }
        val end = _endDate.value?.let { LocalDate.parse(it) }
        Log.d("ViewModel", "Start Date: $start, End Date: $end")
        travelDaysLiveData.value = if (start != null && end != null) {
            ChronoUnit.DAYS.between(start, end).toInt()
        } else {
            0
        }
    }
}
