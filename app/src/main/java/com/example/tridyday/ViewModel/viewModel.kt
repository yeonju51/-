package com.example.tridyday.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import com.example.tridyday.Model.Travel.Schedule.Locate
import java.time.LocalTime

val UNLOCATE= Travel.Schedule.Locate("","",0.0,0.0,"")

class ViewModel: ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<List<Travel>>()
    val travels: LiveData<List<Travel>> get() = _travels

    private val _schedule = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedule: LiveData<MutableList<Travel.Schedule>> get() = _schedule

    private val _locate = MutableLiveData<Travel.Schedule.Locate>(UNLOCATE)
    val locate: LiveData<Travel.Schedule.Locate> get() = _locate

    fun setSchedule(newTitle: String,
                    newDay: Int,
                    newStartTime: LocalTime,
                    newEndTime: LocalTime,
                    newMemo: String) {
        val currentLocate = _locate.value ?: UNLOCATE

        val newSchedule = Travel.Schedule(
            title = newTitle,
            day = newDay,
            startTime = newStartTime,
            endTime = newEndTime,
            memo = newMemo,
            locate = currentLocate
        )
        repository.postSchedule(newSchedule)
    }

    fun setLocate(newName:String,
                  newID : String,
                  newLat : Double,
                  newLng: Double,
                  newPlace:String){

        val newLocate = _locate.value?.let{
            it.name = newName
            it.id = newID
            it.lat = newLat
            it.lng = newLng
            it.place = newPlace
            _locate.value
        } ?: UNLOCATE

        repository.postLocate(newLocate)
    }
    // 새로운 여행 데이터를 Firebase에 저장
    fun addTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.saveTravel(travel, onSuccess, onFailure)
    }

    // LiveData를 사용하면 데이터가 변경될 때 이를 구독하고 있는 UI 컴포넌트(예: Activity, Fragment)가 자동으로 업데이트
    fun updateTravels(newTravels: List<Travel>) {
        _travels.value = newTravels
    }

}