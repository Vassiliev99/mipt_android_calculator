package ru.mipt.android_calculator.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.mipt.android_calculator.presentation.common.BaseActivity
import ru.mipt.android_calculator.R
import ru.mipt.android_calculator.presentation.settings.SettingsActivity
import ru.mipt.android_calculator.databinding.MainActivityBinding
import ru.mipt.android_calculator.di.SettingsDaoProvider
import ru.mipt.android_calculator.presentation.history.HistoryActivity
import ru.mipt.android_calculator.presentation.settings.SettingsViewModel

class MainActivity : BaseActivity() {
    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(SettingsDaoProvider.getDao(this@MainActivity)) as T
            }
        }
    }
    private val viewBinding by viewBinding(MainActivityBinding::bind)

//    private val getResult: ActivityResultLauncher<Int> =
//        registerForActivityResult(Result()) { result ->
//            Toast.makeText(this, "result $result", Toast.LENGTH_SHORT).show()
//        }

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
            textView.setOnClickListener { viewModel.onNumberClick(index) }
        }

        viewBinding.mainMinus.setOnClickListener {
            viewModel.onOperatorClick(Operator.MINUS)
        }
        viewBinding.mainPlus.setOnClickListener {
            viewModel.onOperatorClick(Operator.PLUS)
        }
        viewBinding.mainMultiply.setOnClickListener {
            viewModel.onOperatorClick(Operator.MULTIPLY)
        }
        viewBinding.mainDivide.setOnClickListener {
            viewModel.onOperatorClick(Operator.DIVIDE)
        }
        viewBinding.mainPoint.setOnClickListener {
            viewModel.onOperatorClick(Operator.POINT)
        }

        viewBinding.mainErase.setOnClickListener {
            viewModel.onEraseClick()
        }
        viewBinding.mainClear.setOnClickListener {
            viewModel.onClearClick()
        }
        viewBinding.mainEquals.setOnClickListener {
            viewModel.onEqualsClick()
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
        startActivity(Intent(this, HistoryActivity::class.java))
    }

}

