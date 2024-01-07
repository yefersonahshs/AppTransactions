package com.example.apptransactions.domain.usecase

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.data.model.TransactionResponse
import javax.inject.Inject

class AuthorizeTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: TransactionModel): TransactionResponse {
        return repository.authorizeTransaction(transaction)
    }
}