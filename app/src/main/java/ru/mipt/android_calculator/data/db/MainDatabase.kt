package ru.mipt.android_calculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.mipt.android_calculator.data.db.history.HistoryItemDao
import ru.mipt.android_calculator.data.db.history.HistoryItemEntity

@Database(
    entities = [HistoryItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MainDatabase: RoomDatabase() {

    abstract val historyItemDao: HistoryItemDao

    companion object {

        fun create(context: Context): MainDatabase {
            return Room.databaseBuilder(context, MainDatabase::class.java, "main_database").build()
        }
    }
}