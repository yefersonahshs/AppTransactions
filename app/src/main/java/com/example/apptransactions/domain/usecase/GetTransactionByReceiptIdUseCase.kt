package com.example.apptransactions.domain.usecase

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.domain.model.Transaction
import javax.inject.Inject


class GetTransactionByReceiptIdUseCase  @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(receiptId: String): Transaction? {
        return repository.getTransactionByReceiptId(receiptId)
    }
}