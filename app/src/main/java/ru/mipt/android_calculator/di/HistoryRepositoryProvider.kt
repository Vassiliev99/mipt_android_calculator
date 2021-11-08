package ru.mipt.android_calculator.di

import android.content.Context
import ru.mipt.android_calculator.data.HistoryRepositoryImpl
import ru.mipt.android_calculator.domain.HistoryRepository

object HistoryRepositoryProvider {

    private var repository: HistoryRepository? = null

    fun get(context: Context): HistoryRepository {
        return repository ?: HistoryRepositoryImpl(DatabaseProvider.get(context).historyItemDao)
            .also { repository = it }
    }
}