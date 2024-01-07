package com.example.apptransactions.domain.usecase

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.model.TransactionResponse
import javax.inject.Inject


class GetTransactionByReceiptIdUseCase  @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(receiptId: String): TransactionResponse? {
        return repository.getTransactionByReceiptId(receiptId)
    }
}