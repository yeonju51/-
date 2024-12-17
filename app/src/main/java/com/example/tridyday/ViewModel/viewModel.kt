package com.example.tridyday.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//위치 등록후 스케쥴 로 넘어갈 데이터
var newLocate = Travel.Schedule.Locate("", "", 0.0, 0.0, "")

class ViewModel : ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<MutableList<Travel>>()
    val travels: LiveData<MutableList<Travel>> get() = _travels

    private val _schedules = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedules: LiveData<MutableList<Travel.Schedule>> get() = _schedules

    private var id: String? = null
    private var day: Int? = null

    init {
        repository.observeTravels(_travels)
    }

    fun bringId(travelId: String) {
        id = travelId
    }

    fun bringDay(travelDay: Int) {
        day = travelDay
    }

    fun retriveId(): String? {
        return id
    }

    fun retriveDay(): Int? {
        return day
    }

    fun addSchedule(schedule: Travel.Schedule) {
        id?.let {
            val nonNullId = it
            repository.postSchedule(nonNullId, schedule)
        }
    }

    fun observeSchedules(travelId: String, day: Int) {
        repository.observeSchedule(travelId, _schedules) { schedule ->
            schedule.day == day
        }
    }

    // 여행 데이터를 추가
    fun addTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.saveTravel(travel, {
            // 성공 시 _travels 업데이트
            val currentTravels = _travels.value?.toMutableList() ?: mutableListOf()
            currentTravels.add(travel)
            _travels.value = currentTravels
            onSuccess()
        }, onFailure)
    }


    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate

    // 값 변경 가능
    fun setStartDate(date: String) {
        _startDate.value = date
    }
    fun setEndDate(date: String) {
        _endDate.value = date
    }

}
