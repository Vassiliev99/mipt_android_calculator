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

    companion object {
        private const val PRECISION_NUMBER = "PRECISION_NUMBER"
        const val DEFAULT_PRECISION = 4
    }

}