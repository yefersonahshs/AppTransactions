package com.example.apptransactions.ui

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransactions.data.model.TransactionModel
import com.example.apptransactions.data.model.TransactionResponse
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

    private val _transactions = MutableLiveData<List<TransactionResponse>?>()
    val transactions: LiveData<List<TransactionResponse>?> get() = _transactions

    private val _searchResult = MutableLiveData<TransactionResponse?>()
    val searchResult: LiveData<TransactionResponse?> get() = _searchResult

    private val _isShow = MutableLiveData<Boolean?>()
    val isShow: MutableLiveData<Boolean?> get() = _isShow

    private val _message = MutableLiveData<String?>()
    val message: MutableLiveData<String?> get() = _message


    fun authorizeTransaction(transaction: TransactionModel) {
        viewModelScope.launch {
            try {
                val result = authorizeTransactionUseCase(transaction)

                if (result.statusCode == "99") {
                    _isShow.value = true
                    _message.value = result.statusDescription
                } else {
                    _isShow.value = false
                }
            } catch (e: Exception) {
                _isShow.value = true
                _message.value = "Datos Incorrectos "
            }
        }
    }

    fun cancelTransaction(transaction: TransactionResponse) {
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

