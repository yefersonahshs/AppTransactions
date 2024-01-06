package com.example.apptransactions.data.local.repository

import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.local.database.dao.TransactionDao
import com.example.apptransactions.data.local.database.entities.TransactionEntity
import com.example.apptransactions.domain.model.Transaction


class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun authorizeTransaction(transaction: Transaction): Transaction {
        val entity = TransactionEntity(
            receiptId = "123",
            rrn = "456",
            statusCode = "00",
            statusDescription = "Aprobada"
        )
        transactionDao.insertTransaction(entity)
        return entity.toTransactionModel()
    }

    override suspend fun cancelTransaction(receiptId: String, rrn: String): Transaction {
        val entity = transactionDao.getTransactionByReceiptId(receiptId)
        if (entity != null && entity.statusCode == "00") {
            entity.statusCode = "99"
            entity.statusDescription = "Anulada"
            transactionDao.updateTransaction(entity)
            return entity.toTransactionModel()
        } else {
            throw Exception("No se puede anular la transacción")
        }
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions().map { it.toTransactionModel() }
    }

    override suspend fun getTransactionByReceiptId(receiptId: String): Transaction? {
        return transactionDao.getTransactionByReceiptId(receiptId)?.toTransactionModel()
    }

    private fun TransactionEntity.toTransactionModel(): Transaction {
        return Transaction(receiptId, rrn, statusCode, statusDescription)
    }
}
