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
        val title: String,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val memo: String,
        val locate: Locate
    ) {
        data class Locate(
            val name: String,
            val iD: String,
            val lat: Double,
            val lng: Double,
            val place: String
        )
    }
}
