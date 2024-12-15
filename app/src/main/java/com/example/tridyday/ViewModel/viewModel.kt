package com.example.tridyday.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel

val UNLOCATE = Travel.Schedule.Locate("", "", 0.0, 0.0, "")

class ViewModel : ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<MutableList<Travel>>()
    val travels: LiveData<MutableList<Travel>> get() = _travels

    private val _schedules = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedules: LiveData<MutableList<Travel.Schedule>> get() = _schedules

    init {
        repository.observeSchedule(_schedules)
        repository.observeTravels(_travels) // Firebase의 여행 데이터를 실시간으로 관찰
    }

    private val _locate = MutableLiveData<Travel.Schedule.Locate>(UNLOCATE)
    val locate: LiveData<Travel.Schedule.Locate> get() = _locate

    fun setSchedule(schedule: Travel.Schedule) {
        _schedules.value?.add(schedule)  // 새로운 일정을 추가
        _schedules.value = _schedules.value  // LiveData를 갱신
    }

    private val _travelDaysLiveData = MutableLiveData<Int>()
    val travelDaysLiveData: LiveData<Int> get() = _travelDaysLiveData

    private val _dayButtonsLiveData = MutableLiveData<List<Int>>()
    val dayButtonsLiveData: LiveData<List<Int>> get() = _dayButtonsLiveData

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchTravelDays(travelId: String) {
        // Repository에서 travelDays 값을 가져와서 업데이트
        repository.getTravelDays(_travelDaysLiveData)
    }

    fun generateDayButtons() {
        // travelDaysLiveData의 값이 변경되면 이를 가공하여 버튼 리스트를 생성
        val totalDays = _travelDaysLiveData.value ?: 0
        if (totalDays > 0) {
            _dayButtonsLiveData.value = (1..totalDays).toList()
        } else {
            _dayButtonsLiveData.value = emptyList() // 값이 없으면 빈 리스트 전달
        }
    }

    // 여행 데이터 추가
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

    private var selTravel = 0

    fun sendSelTravel(sel: Int){
        selTravel = sel
    }
    fun recvSelTravel():Int {
        return selTravel
    }
}
