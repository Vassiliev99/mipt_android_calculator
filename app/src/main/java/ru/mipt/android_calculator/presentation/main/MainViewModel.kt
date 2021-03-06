package ru.mipt.android_calculator.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mipt.android_calculator.domain.HistoryRepository
import ru.mipt.android_calculator.domain.SettingsDao
import ru.mipt.android_calculator.domain.calculateExpression
import ru.mipt.android_calculator.domain.entity.HistoryItem

class MainViewModel(
    private val settingsDao: SettingsDao,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private var expression: String = ""

    private val _expressionState = MutableLiveData<String>()
    val expressionState: LiveData<String> = _expressionState

    private val _resultState = MutableLiveData<String>()
    val resultState: LiveData<String> = _resultState

    private val _precisionNumber = MutableLiveData<Int>()
    private val _vibrationNumber = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            _precisionNumber.value = settingsDao.getPrecisionNumber()
            _vibrationNumber.value = settingsDao.getVibrationNumber()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "onCleared")
    }

    fun onNumberClick(number: Int) {
        expression += number.toString()
        _expressionState.value = expression
        _resultState.value = _precisionNumber.value?.let { calculateExpression(expression, it) }
    }

    fun onOperatorClick(operator: Operator) {
        if ((expression.isNotEmpty() && expression.last().isDigit())
            || (expression.isEmpty() && operator == Operator.MINUS)) {
            expression += operator.symbol
            _expressionState.value = expression
            _resultState.value = ""
        }
    }

    fun onEraseClick() {
        if (expression.isNotEmpty()) {
            expression = expression.dropLast(1)
            _expressionState.value = expression

            when {
                expression.isEmpty() -> {
                    _resultState.value = ""
                }
                expression.last().isDigit() -> {
                    _resultState.value =
                        _precisionNumber.value?.let { calculateExpression(expression, it) }
                }
                else -> {
                    _resultState.value = ""
                }
            }
        }
    }

    fun onClearClick() {
        expression = ""
        _expressionState.value = ""
        _resultState.value = ""
    }

    fun onEqualsClick() {
        if (expression.isNotEmpty() && expression.last().isDigit()) {
            viewModelScope.launch {
                historyRepository.add(HistoryItem(expression, _resultState.value.toString()))
            }
            expression = _resultState.value.toString()
            _expressionState.value = expression
        }
    }

    fun onStart() {
        viewModelScope.launch {
            _precisionNumber.value = settingsDao.getPrecisionNumber()
            if (expression.isNotEmpty() && expression.last().isDigit()) {
                _resultState.value = _precisionNumber.value?.let { calculateExpression(expression, it) }
            }
            _vibrationNumber.value = settingsDao.getVibrationNumber()
        }
    }

    fun onHistoryResult(item: HistoryItem?) {
        if (item != null) {
            expression = item.expression
            _expressionState.value = expression
            _resultState.value = item.result
        }
    }
}

enum class Operator(val symbol: String) {
    MINUS("-"), PLUS("+"), MULTIPLY("*"), DIVIDE("/"), POINT(".")
}