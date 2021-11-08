package ru.mipt.android_calculator.domain

interface SettingsDao {

    suspend fun getPrecisionNumber(): Int
    suspend fun setPrecisionNumber(number: Int)

    suspend fun getVibrationNumber(): Int
    suspend fun setVibrationNumber(number: Int)
}