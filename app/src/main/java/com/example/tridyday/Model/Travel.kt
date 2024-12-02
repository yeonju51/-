package com.example.tridyday.Model

import android.net.Uri

data class Travel(
    val title: String,
    val location: String,
    val startDate: String?,
    val endDate: String?,
    val photoUri: Uri? = null // 사진 URI를 추가
) {
    data class Schedule(
        val title: String,
        val startTime: Int,
        val endTime: Int,
        val memo: String
    ) {
        data class locate(
            val name: String,
            val iD: String,
            val lat: Double,
            val lng: Double,
            val place: String
        )
    }
}
