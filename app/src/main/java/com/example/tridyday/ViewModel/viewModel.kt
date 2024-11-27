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