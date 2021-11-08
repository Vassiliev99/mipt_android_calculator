package ru.mipt.android_calculator.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItem(
    val expression: String,
    val result: String
) : Parcelable