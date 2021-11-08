package ru.mipt.android_calculator.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.mipt.android_calculator.presentation.common.BaseActivity
import ru.mipt.android_calculator.R
import ru.mipt.android_calculator.presentation.settings.SettingsActivity
import ru.mipt.android_calculator.databinding.MainActivityBinding
import ru.mipt.android_calculator.di.HistoryRepositoryProvider
import ru.mipt.android_calculator.di.SettingsDaoProvider
import ru.mipt.android_calculator.presentation.history.HistoryActivity
import ru.mipt.android_calculator.presentation.history.HistoryResult
import ru.mipt.android_calculator.presentation.settings.SettingsViewModel

class MainActivity : BaseActivity() {
    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(
                    SettingsDaoProvider.getDao(this@MainActivity),
                    HistoryRepositoryProvider.get(this@MainActivity)
                ) as T
            }
        }
    }
    private val viewBinding by viewBinding(MainActivityBinding::bind)

    private val resultLauncher =
        registerForActivityResult(HistoryResult()) { item ->
            viewModel.onHistoryResult(item)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewBinding.mainSettings.setOnClickListener {
            openSettings()
        }
        viewBinding.mainHistory.setOnClickListener {
            openHistory()
        }

        viewBinding.mainInput.apply {
            showSoftInputOnFocus = false
        }

        listOf(
            viewBinding.mainZero,
            viewBinding.mainOne,
            viewBinding.mainTwo,
            viewBinding.mainThree,
            viewBinding.mainFour,
            viewBinding.mainFive,
            viewBinding.mainSix,
            viewBinding.mainSeven,
            viewBinding.mainEight,
            viewBinding.mainNine
        ).forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.onNumberClick(index)
                vibrate()
            }
        }

        mapOf(
            Operator.PLUS to viewBinding.mainPlus,
            Operator.MINUS to viewBinding.mainMinus,
            Operator.MULTIPLY to viewBinding.mainMultiply,
            Operator.DIVIDE to viewBinding.mainDivide,
            Operator.POINT to viewBinding.mainPoint
        ).forEach { (operator, textView) ->
            textView.setOnClickListener {
                viewModel.onOperatorClick(operator)
                vibrate()
            }
        }


        viewBinding.mainErase.setOnClickListener {
            viewModel.onEraseClick()
            vibrate()
        }
        viewBinding.mainClear.setOnClickListener {
            viewModel.onClearClick()
            vibrate()
        }
        viewBinding.mainEquals.setOnClickListener {
            viewModel.onEqualsClick()
            vibrate()
        }

        viewModel.expressionState.observe(this) { state ->
            viewBinding.mainInput.setText(state)
        }
        viewModel.resultState.observe(this) { state ->
            viewBinding.mainResult.text = state
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openHistory() {
        resultLauncher.launch()
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            Log.d("main", "vibration")
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            Log.d("main", "no vibrator")
        }
    }

}

