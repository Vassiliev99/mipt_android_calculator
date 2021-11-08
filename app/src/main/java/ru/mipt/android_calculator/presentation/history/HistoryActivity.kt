package ru.mipt.android_calculator.presentation.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.mipt.android_calculator.R
import ru.mipt.android_calculator.databinding.HistoryActivityBinding
import ru.mipt.android_calculator.di.SettingsDaoProvider
import ru.mipt.android_calculator.presentation.common.BaseActivity
import ru.mipt.android_calculator.presentation.settings.SettingsViewModel

class HistoryActivity() : BaseActivity() {
    private val viewModel by viewModels<HistoryViewModel>()
    private val viewBinding by viewBinding(HistoryActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)

        viewBinding.historyBack.setOnClickListener {
            finish()
        }

        val historyAdapter = HistoryAdapter(viewModel::onItemClicked)
        with(viewBinding.historyList) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        viewModel.historyItemsState.observe(this) { state ->
            historyAdapter.setData(state)
        }
    }
}