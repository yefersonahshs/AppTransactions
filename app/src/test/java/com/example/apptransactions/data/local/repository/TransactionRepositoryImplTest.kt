package com.example.apptransactions.data.local.repository

import com.example.apptransactions.data.api.ApiService
import com.example.apptransactions.data.local.database.dao.TransactionDao
import com.example.apptransactions.data.local.database.entities.TransactionEntity
import com.example.apptransactions.data.local.repository.TransactionRepositoryImpl
import com.example.apptransactions.data.model.CancelRequest
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.data.model.TransactionResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TransactionRepositoryImplTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var apiService: ApiService
    private lateinit var transactionRepository: TransactionRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        transactionDao = mock(TransactionDao::class.java)
        apiService = mock(ApiService::class.java)
        transactionRepository = TransactionRepositoryImpl(transactionDao, apiService)
    }


    @Test
    fun `authorizeTransaction should insert transaction in the local database`() = runBlockingTest {
        val transaction = TransactionModel("","","","","")
        val response = TransactionResponse("","","","",)

        `when`(apiService.authorizeTransaction(transaction)).thenReturn(response)

        transactionRepository.authorizeTransaction(transaction)

    }

    @Test
    fun testCancelTransaction() = runBlockingTest{
        val receiptId = "123456"
        val rrn = "789012"
        val cancelRequest = CancelRequest(receiptId, rrn)
        val existingTransaction = TransactionEntity(
            receiptId = receiptId,
            rrn = rrn,
            statusCode = "200",
            statusDescription = "Aprobada"
        )
        `when`(apiService.cancelTransaction(cancelRequest))
            .thenReturn(TransactionResponse(receiptId, rrn, "200", "Anulada"))
        `when`(transactionDao.getTransactionByReceiptId(receiptId))
            .thenReturn(existingTransaction)

        transactionRepository.cancelTransaction(receiptId, rrn)

    }

}
