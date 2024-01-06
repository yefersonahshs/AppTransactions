package com.example.apptransactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransactions.domain.model.Transaction
import com.example.apptransactions.domain.usecase.AuthorizeTransactionUseCase
import com.example.apptransactions.domain.usecase.CancelTransactionUseCase
import com.example.apptransactions.domain.usecase.GetAllTransactionsUseCase
import com.example.apptransactions.domain.usecase.GetTransactionByReceiptIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val authorizeTransactionUseCase: AuthorizeTransactionUseCase,
    private val cancelTransactionUseCase: CancelTransactionUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getTransactionByReceiptIdUseCase: GetTransactionByReceiptIdUseCase
) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>?>()
    val transactions: MutableLiveData<List<Transaction>?> get() = _transactions

    private val _searchResult = MutableLiveData<Transaction?>()
    val searchResult: LiveData<Transaction?> get() = _searchResult

    fun authorizeTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val result = authorizeTransactionUseCase(transaction)
        }
    }

    fun cancelTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val result = cancelTransactionUseCase(transaction.receiptId, transaction.rrn)
        }
    }

    fun getAllTransactions() {
        viewModelScope.launch {
            _transactions.value = getAllTransactionsUseCase()
        }
    }

    fun searchTransactionByReceiptId(receiptId: String) {
        viewModelScope.launch {
            val result = getTransactionByReceiptIdUseCase(receiptId)
            _searchResult.postValue(result)
        }
    }
}