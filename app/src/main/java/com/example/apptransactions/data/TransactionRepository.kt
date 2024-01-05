package com.example.apptransactions.data

interface TransactionRepository {

    suspend fun authorizeTransaction(transaction: TransactionModel): TransactionModel

    suspend fun cancelTransaction(receiptId: String, rrn: String): TransactionModel

    suspend fun getAllTransactions(): List<TransactionModel>

    suspend fun getTransactionByReceiptId(receiptId: String): TransactionModel?
}
