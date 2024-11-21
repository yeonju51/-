package com.example.tridyday.Model

data class Schedule(val title: String,
                    val startHour: Int,
                    val startMinute: Int,
                    val endHour: Int,
                    val endMinute: Int,
                    val memo: String) {

}