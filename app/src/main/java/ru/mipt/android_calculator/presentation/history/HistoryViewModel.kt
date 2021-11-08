package ru.mipt.android_calculator.presentation.history

import android.text.method.SingleLineTransformationMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mipt.android_calculator.domain.HistoryRepository
import ru.mipt.android_calculator.domain.entity.HistoryItem
import ru.mipt.android_calculator.presentation.settings.SingleLiveEvent

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState: LiveData<List<HistoryItem>> = _historyItemsState

    private val _closeWithResult = SingleLiveEvent<HistoryItem>()
    val closeWithResult: LiveData<HistoryItem> = _closeWithResult


    init {
        viewModelScope.launch {
            _historyItemsState.value = historyRepository.getAll()
        }
    }

    fun onItemClicked(historyItem: HistoryItem) {
        _closeWithResult.value = historyItem
    }
}

