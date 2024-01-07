package com.example.apptransactions.data

import com.example.apptransactions.data.model.TransactionResponse
import com.example.apptransactions.data.model.CancelRequest
import com.example.apptransactions.data.model.TransactionModel

interface TransactionRepository {

    suspend fun authorizeTransaction(transaction: TransactionModel): TransactionResponse

    suspend fun cancelTransaction(receiptId: String, rrn: String): TransactionResponse

    suspend fun getAllTransactions(): List<TransactionResponse>

    suspend fun getTransactionByReceiptId(receiptId: String): TransactionResponse?
}