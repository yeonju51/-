package com.example.tridyday.Model

data class Travel(
    val title: String,
    val location: String = "",  // 기본값 추가
    val startDate: String,
    val endDate: String,
    val photoUri: String? = null
)
