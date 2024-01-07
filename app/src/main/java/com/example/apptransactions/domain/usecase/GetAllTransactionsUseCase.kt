package com.example.apptransactions.domain.usecase
import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.model.TransactionResponse
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend operator fun invoke(): List<TransactionResponse> {
        return repository.getAllTransactions()
    }
}