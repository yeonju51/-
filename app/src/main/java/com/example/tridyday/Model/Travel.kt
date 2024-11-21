package com.example.tridyday.Model

data class Travel(
    val title: String,
    val location: String,   // location 추가
    val startDate: String,
    val endDate: String,
    val photoUri: String? = null  // optional photoUri
)
