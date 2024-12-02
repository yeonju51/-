package com.example.tridyday.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel

val UNLOCATE= locateClass("","",0.0,0.0,"")

class ViewModel: ViewModel() {
    private val _schedule = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedule: LiveData<MutableList<Travel.Schedule>> get() = _schedule

    private val _locate = MutableLiveData<Travel.Schedule.locate>(UNLOCATE)
    val locate: LiveData<Travel.Schedule.locate> get() = _locate

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