package ru.mipt.android_calculator.presentation.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mipt.android_calculator.data.SettingsDaoImpl
import ru.mipt.android_calculator.domain.SettingsDao

class SettingsViewModel(
    private val settingsDao: SettingsDao
) : ViewModel() {
    private val _precisionNumber = MutableLiveData<Int>(SettingsDaoImpl.DEFAULT_PRECISION)
    val precisionNumberState: LiveData<Int> = _precisionNumber

    private val _vibrationNumber = MutableLiveData<Int>(SettingsDaoImpl.DEFAULT_VIBRATION)
    val vibrationNumberState: LiveData<Int> = _vibrationNumber

    fun onPrecisionNumberChanged(number: Int) {
        Log.d("sett3", number.toString())
        _precisionNumber.value = number
        viewModelScope.launch {
            settingsDao.setPrecisionNumber(number)
        }
    }

    fun onVibrationNumberChanged(number: Int) {
        Log.d("sett2", number.toString())
        _vibrationNumber.value = number
        viewModelScope.launch {
            settingsDao.setVibrationNumber(number)
        }
    }

    fun onStart() {
        Log.d("set", _precisionNumber.value.toString() + " " + _vibrationNumber.value.toString())
    }

    init {
        viewModelScope.launch {
            _precisionNumber.value = settingsDao.getPrecisionNumber()
            _vibrationNumber.value = settingsDao.getVibrationNumber()
        }
    }
}