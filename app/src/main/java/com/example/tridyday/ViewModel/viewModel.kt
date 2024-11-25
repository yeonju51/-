package com.example.tridyday.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Schedule

class scheduleViewModel: ViewModel() {
    private val _schedule = MutableLiveData<MutableList<Schedule>>()
    val schedule: LiveData<MutableList<Schedule>> get() = _schedule

    private val repository = Repository()
    init {
        // repository.observeSchedule(_schedule)

    }

    private fun modifySchedule(index: Int) {

    }

    fun addSchedule(title: String, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, memo: String) {

    }
}