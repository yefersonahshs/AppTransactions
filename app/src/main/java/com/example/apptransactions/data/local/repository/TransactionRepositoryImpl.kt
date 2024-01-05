package com.example.apptransactions.data.local.repository


class TransactionRepositoryImpl(private val transactionDao: TransactionDao) : TransactionRepository {

    override suspend fun authorizeTransaction(transaction: TransactionModel): TransactionModel {
    }

    override suspend fun cancelTransaction(receiptId: String, rrn: String): TransactionModel {
    }

    override suspend fun getAllTransactions(): List<TransactionModel> {
    }

    override suspend fun getTransactionByReceiptId(receiptId: String): TransactionModel? {
    }
}
