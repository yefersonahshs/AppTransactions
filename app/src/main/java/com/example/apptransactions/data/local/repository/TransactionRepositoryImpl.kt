package com.example.apptransactions.data.local.repository
import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.api.ApiService
import com.example.apptransactions.data.local.database.dao.TransactionDao
import com.example.apptransactions.data.local.database.entities.TransactionEntity
import com.example.apptransactions.data.model.CancelRequest
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.data.model.TransactionResponse

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun authorizeTransaction(transaction: TransactionModel): TransactionResponse {
        val response = apiService.authorizeTransaction(transaction)

        val entity = TransactionEntity(
            receiptId = response.receiptId,
            rrn = response.rrn,
            statusCode = response.statusCode,
            statusDescription = response.statusDescription
        )
        transactionDao.insertTransaction(entity)

        return response
    }

    override suspend fun cancelTransaction(receiptId: String, rrn: String): TransactionResponse {
        val cancelRequest = CancelRequest(receiptId, rrn)
        val response = apiService.cancelTransaction(cancelRequest)
        val existingTransaction = transactionDao.getTransactionByReceiptId(receiptId)

        existingTransaction?.apply {
            statusCode = response.statusCode
            statusDescription = "Anulada"
        }
        existingTransaction?.let { transactionDao.updateTransaction(it) }

        return response
    }

    override suspend fun getAllTransactions(): List<TransactionResponse> {
        return transactionDao.getAllTransactions().map { it.toTransactionModel() }
    }

    override suspend fun getTransactionByReceiptId(receiptId: String): TransactionResponse? {
        return transactionDao.getTransactionByReceiptId(receiptId)?.toTransactionModel()
    }

    private fun TransactionEntity.toTransactionModel(): TransactionResponse {
        return TransactionResponse(receiptId, rrn, statusCode, statusDescription)
    }
}
