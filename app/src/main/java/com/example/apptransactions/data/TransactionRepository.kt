package com.example.apptransactions.data

import com.example.apptransactions.domain.model.Transaction

interface TransactionRepository {

        suspend fun authorizeTransaction(transaction: Transaction): Transaction

        suspend fun cancelTransaction(receiptId: String, rrn: String): Transaction

        suspend fun getAllTransactions(): List<Transaction>

        suspend fun getTransactionByReceiptId(receiptId: String): Transaction?
    }
