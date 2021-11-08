package ru.mipt.android_calculator.domain

import ru.mipt.android_calculator.domain.entity.HistoryItem

interface HistoryRepository {

    suspend fun add(historyItem: HistoryItem)

    suspend fun getAll(): List<HistoryItem>
}