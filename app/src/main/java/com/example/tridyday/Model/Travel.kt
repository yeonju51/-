package com.example.tridyday.Model

import android.net.Uri
import java.time.LocalTime

data class Travel(
    var id: String? = null,
    var title: String = "",
    var location: String = "",
    var startDate: String? = null,
    var endDate: String? = null,
    var photoUri: String? = null,
    var days:Int = 0
) {

    data class Schedule(
        var title: String = "",
        var day: Int = 0,
        var startTime: String? = null,
        var endTime: String? = null,
        var memo: String? = null,
        var locate: Locate = Locate()
    ) {
        data class Locate(
            var id: String = "",
            var name: String = "",
            var lat: Double = 0.0,
            var lng: Double = 0.0,
            var place: String = ""
        ){
            fun checkLocate(): Int{
                return if(id == "") 0 else 1
            }

            fun reset(){
                change("","",0.0,0.0,"")
            }

            fun change(Pid:String,
                       Pname:String,
                       Plat:Double,
                       Plng:Double,
                       Pplace:String
            ) {
                id = Pid
                name = Pname
                lat = Plat
                lng = Plng
                place = Pplace
            }
        }

    }
}
