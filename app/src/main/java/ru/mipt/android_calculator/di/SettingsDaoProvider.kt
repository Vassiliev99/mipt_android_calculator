package ru.mipt.android_calculator.di

import android.content.Context
import ru.mipt.android_calculator.data.SettingsDaoImpl
import ru.mipt.android_calculator.domain.SettingsDao

object SettingsDaoProvider {

    private var dao: SettingsDao? = null


    fun getDao(context: Context):SettingsDao {
        return dao ?: SettingsDaoImpl(
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )
        ).also {
            dao = it
        }
    }
}