package com.example.tridyday.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Travel

class ViewModel : ViewModel() {

    private val _travelList = MutableLiveData<List<Travel>>()
    val travelList: LiveData<List<Travel>> get() = _travelList

    private val travels = mutableListOf<Travel>()

    fun addTravel(
        travel: Travel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            travels.add(travel)
            _travelList.value = travels
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
