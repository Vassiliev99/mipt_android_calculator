package ru.mipt.android_calculator.domain

interface SettingsDao {

    suspend fun getPrecisionNumber(): Int
    suspend fun setPrecisionNumber(number: Int)
}