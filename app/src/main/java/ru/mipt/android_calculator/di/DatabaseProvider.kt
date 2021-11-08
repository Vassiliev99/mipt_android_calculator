package ru.mipt.android_calculator.di

import android.content.Context
import ru.mipt.android_calculator.data.db.MainDatabase

object DatabaseProvider {

    private var db: MainDatabase? = null

    fun get(context: Context): MainDatabase {
        return db ?: MainDatabase.create(context).also { db = it }
    }
}

