package ru.mipt.android_calculator.presentation.settings

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

    fun onPrecisionNumberChanged(number: Int) {
        _precisionNumber.value = number
        viewModelScope.launch {
            settingsDao.setPrecisionNumber(number)
        }
    }

    init {
        viewModelScope.launch {
            _precisionNumber.value = settingsDao.getPrecisionNumber()
        }
    }
}