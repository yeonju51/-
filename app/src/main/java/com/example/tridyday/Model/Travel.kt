package com.example.tridyday.Model

import android.net.Uri
import java.time.LocalTime

data class Travel(
    var title: String,
    var location: String,
    var startDate: String?,
    var endDate: String?,
    var photoUri: Uri? = null // 사진 URI를 추가
) {
    data class Schedule(
        var title: String,
        var day: Int,
        var startTime: LocalTime,
        var endTime: LocalTime,
        var memo: String,
        var locate: Locate
    ) {
        data class Locate(
            var id: String,
            var name: String,
            var lat: Double,
            var lng: Double,
            var place: String
        )

        companion object {
            fun Locate(name: String, id: String, lat: Double, lng: Double, place: String): Travel.Schedule.Locate {
                return Locate(name, id, lat, lng, place)
            }
        }
    }
}
