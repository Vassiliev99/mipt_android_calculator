package ru.mipt.android_calculator.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mipt.android_calculator.databinding.HistoryItemBinding
import ru.mipt.android_calculator.domain.entity.HistoryItem

class HistoryAdapter(
    private val onItemClicked: (HistoryItem) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data: List<HistoryItem> = emptyList()

    fun setData(data: List<HistoryItem>) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = data[position]
        with(holder.bindings) {
            historyItemExpression.text = item.expression
            historyItemResult.text = item.result
            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class HistoryViewHolder(val bindings: HistoryItemBinding) : RecyclerView.ViewHolder(bindings.root) {
    }
}

