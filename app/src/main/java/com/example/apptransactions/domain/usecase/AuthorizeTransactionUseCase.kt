package com.example.apptransactions.domain.usecase

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.domain.model.Transaction
import javax.inject.Inject

class AuthorizeTransactionUseCase  @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction): Transaction {
        return repository.authorizeTransaction(transaction)
    }
}