package com.example.tridyday.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel

val UNLOCATE= Travel.Schedule.Locate("","",0.0,0.0,"")

class ViewModel: ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<List<Travel>>()
    val travels: LiveData<List<Travel>> get() = _travels

    private val _schedule = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedule: LiveData<MutableList<Travel.Schedule>> get() = _schedule

    private val _locate = MutableLiveData<Travel.Schedule.Locate>(UNLOCATE)
    val locate: LiveData<Travel.Schedule.Locate> get() = _locate

    init{
        repository.observeSchedule(_schedule)
    }

    fun addSchedule(newSchedule: Travel.Schedule) {
        val updatedSchedule = _schedule.value ?: mutableListOf()
        updatedSchedule.add(newSchedule)
        _schedule.value = updatedSchedule
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

    // 여행 데이터를 업데이트
    fun updateTravels(newTravels: List<Travel>) {
        _travels.value = newTravels
    }

}