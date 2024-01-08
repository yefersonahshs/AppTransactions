package com.example.apptransactions.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptransactions.data.model.TransactionResponse
import com.example.apptransactions.databinding.ItemTransactionBinding

class TransactionAdapter(
    private val onItemClick: (TransactionResponse) -> Unit
) : ListAdapter<TransactionResponse, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(transaction: TransactionResponse) {
            binding.textViewReceiptId.text = transaction.receiptId
            binding.status.text = transaction.statusDescription
        }
    }
}

private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionResponse>() {
    override fun areItemsTheSame(oldItem: TransactionResponse, newItem: TransactionResponse): Boolean {
        return oldItem.receiptId == newItem.receiptId
    }

    override fun areContentsTheSame(oldItem: TransactionResponse, newItem: TransactionResponse): Boolean {
        return oldItem == newItem
    }
}
