package ru.mipt.android_calculator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mipt.android_calculator.domain.entity.HistoryItem

class HistoryViewModel : ViewModel() {

    private val historyItems: List<HistoryItem> = listOf(
        HistoryItem( "2+35+5435+123", "1233"),
        HistoryItem( "2+35+54+123", "1233123"),
        HistoryItem( "2+35+5435+123", "12313123123"),
        HistoryItem( "2+35+543+5+123", "452253"),
        HistoryItem( "2+35+5435+123", "12313123123"),
        HistoryItem( "2+35+54+4*35+123", "1231323")
    )

    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState: LiveData<List<HistoryItem>> = _historyItemsState

    init {
        _historyItemsState.value = historyItems
    }

    fun onItemClicked(historyItem: HistoryItem) {

    }
}

