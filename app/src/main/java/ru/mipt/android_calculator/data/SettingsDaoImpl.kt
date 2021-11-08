package ru.mipt.android_calculator.data

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mipt.android_calculator.domain.SettingsDao

class SettingsDaoImpl(
    private val preferences: SharedPreferences
) : SettingsDao {
    override suspend fun getPrecisionNumber(): Int = withContext(Dispatchers.IO) {
        preferences.getInt(PRECISION_NUMBER, DEFAULT_PRECISION)
    }

    override suspend fun setPrecisionNumber(number: Int) = withContext(Dispatchers.IO) {
        preferences.edit().putInt(PRECISION_NUMBER, number).apply()
    }

    override suspend fun getVibrationNumber(): Int = withContext(Dispatchers.IO) {
        preferences.getInt(VIBRATION_NUMBER, DEFAULT_VIBRATION)
    }

    override suspend fun setVibrationNumber(number: Int) = withContext(Dispatchers.IO) {
        preferences.edit().putInt(VIBRATION_NUMBER, number).apply()
    }

    companion object {
        private const val PRECISION_NUMBER = "PRECISION_NUMBER"
        private const val VIBRATION_NUMBER = "VIBRATION_NUMBER"
        const val DEFAULT_PRECISION = 4
        const val DEFAULT_VIBRATION = 5
    }

}