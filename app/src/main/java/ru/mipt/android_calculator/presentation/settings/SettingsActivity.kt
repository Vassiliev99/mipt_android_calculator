package ru.mipt.android_calculator.presentation.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.mipt.android_calculator.presentation.common.BaseActivity
import ru.mipt.android_calculator.R
import ru.mipt.android_calculator.di.SettingsDaoProvider
import ru.mipt.android_calculator.databinding.SettingsActivityBinding

class SettingsActivity() : BaseActivity() {
    private val viewModel by viewModels<SettingsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(SettingsDaoProvider.getDao(this@SettingsActivity)) as T
            }
        }
    }
    private val viewBinding by viewBinding(SettingsActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        viewBinding.settingsBack.setOnClickListener {
            finish()
        }

        viewModel.vibrationNumberState.observe(this) { state ->
            viewBinding.settingsVibration.setText((state * 10).toString())
            viewBinding.settingsVibrationBar.setProgress(state)
        }

        viewModel.precisionNumberState.observe(this) { state ->
            viewBinding.settingsPrecision.setText(state.toString())
            viewBinding.settingsPrecisionBar.setProgress(state)
        }

        viewBinding.settingsPrecisionBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                viewModel.onPrecisionNumberChanged(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })



        viewBinding.settingsVibrationBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                viewModel.onVibrationNumberChanged(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })


    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

}