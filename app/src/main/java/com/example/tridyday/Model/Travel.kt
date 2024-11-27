package com.example.tridyday.Model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Travel(
    val title: String,
    val location: String,
    val startDate: String?,
    val endDate: String?,
    val photoUri: Uri? = null // 사진 URI를 추가
) : Parcelable
