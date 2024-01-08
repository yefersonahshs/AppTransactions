package com.example.apptransactions.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.data.model.TransactionResponse
import com.example.apptransactions.domain.usecase.AuthorizeTransactionUseCase
import com.example.apptransactions.domain.usecase.CancelTransactionUseCase
import com.example.apptransactions.domain.usecase.GetAllTransactionsUseCase
import com.example.apptransactions.domain.usecase.GetTransactionByReceiptIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val authorizeTransactionUseCase: AuthorizeTransactionUseCase = mockk()
    private val cancelTransactionUseCase: CancelTransactionUseCase = mockk()
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase = mockk()
    private val getTransactionByReceiptIdUseCase: GetTransactionByReceiptIdUseCase = mockk()

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(
            authorizeTransactionUseCase,
            cancelTransactionUseCase,
            getAllTransactionsUseCase,
            getTransactionByReceiptIdUseCase
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `authorizeTransaction should update LiveData`() = runBlockingTest {
        // Given
        val transactionModel = TransactionModel("","","","","")
        val expectedResult = TransactionResponse("","","","")
        coEvery { authorizeTransactionUseCase(transactionModel) } returns expectedResult

        // When
        viewModel.authorizeTransaction(transactionModel)

        // Then
        assert(viewModel.isShow.value == false)
        assert(viewModel.transactions.value == null)
        assert(viewModel.searchResult.value == null)
        assert(viewModel.message.value == null)
    }

    @Test
    fun `cancelTransaction should call CancelTransactionUseCase`() = runBlockingTest {
        val transactionResponse = TransactionResponse("","","","")
        viewModel.cancelTransaction(transactionResponse)

    }



    @Test
    fun `searchTransactionByReceiptId should update LiveData`() = runBlockingTest {
        // Given
        val receiptId = "123456"
        val expectedResult = mockk<TransactionResponse>()
        coEvery { getTransactionByReceiptIdUseCase(receiptId) } returns expectedResult

        // When
        viewModel.searchTransactionByReceiptId(receiptId)

        // Then
        assert(viewModel.isShow.value == null)
        assert(viewModel.transactions.value == null)
        assert(viewModel.searchResult.value == expectedResult)
        assert(viewModel.message.value == null)
    }
}