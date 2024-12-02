package com.example.tridyday.Model

import android.net.Uri

data class Travel(
    var title: String,
    var location: String,
    var startDate: String?,
    var endDate: String?,
    var photoUri: Uri? = null // 사진 URI를 추가
) {
    data class Schedule(
        var title: String,
        var startTime: Int,
        var endTime: Int,
        var memo: String
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
