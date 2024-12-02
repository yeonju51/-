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
        var startTime: LocalTime,
        var endTime: LocalTime,
        var memo: String,
        var locate: Locate
    ) {
        data class Locate(
            var name: String,
            var id: String,
            var lat: Double,
            var lng: Double,
            var place: String
        )
    }
}
