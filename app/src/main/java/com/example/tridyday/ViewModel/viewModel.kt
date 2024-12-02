package com.example.tridyday.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Schedule

class scheduleViewModel : ViewModel() {
    private val _schedule = MutableLiveData<List<Schedule>>()
    val schedule: LiveData<List<Schedule>> get() = _schedule

    private val repository = Repository()

    init {
        repository.getSchedules { schedules ->
            _schedule.postValue(schedules) // List<Schedule>을 바로 전달
        }
    }

//    private fun modifySchedule(index: Int, newValue: Char) {
//        _schedule.value = _schedule.value?.let {
//            val title = newValue
//        }
//
//        repository.postSchedule(_schedule.value)
//    }

    fun addSchedule(title: String, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, memo: String) {

    }
}


val UNLOCATE= locateClass("","",0.0,0.0,"")

class locateViewModel: ViewModel() {
    private val _schedule = MutableLiveData<MutableList<Schedule>>()
    val schedule: LiveData<MutableList<Schedule>> get() = _schedule



    private val _locate = MutableLiveData<locateClass>(UNLOCATE)
    val locate: LiveData<locateClass> get() = _locate

    private val repository = Repository()
    init{
        repository.observeLocate(_locate)
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
}

data class locateClass(var name: String,
                       var id: String,
                       var lat: Double,
                       var lng: Double,
                       var place: String) {

}