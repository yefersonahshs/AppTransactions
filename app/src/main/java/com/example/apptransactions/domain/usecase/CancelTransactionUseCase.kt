package com.example.apptransactions.domain.usecase

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.domain.model.Transaction
import javax.inject.Inject

class CancelTransactionUseCase  @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(receiptId: String, rrn: String): Transaction {
        return repository.cancelTransaction(receiptId, rrn)
    }
}