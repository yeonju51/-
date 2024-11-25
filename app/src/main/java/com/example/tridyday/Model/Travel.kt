package com.example.tridyday.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Travel(
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String
) : Parcelable
