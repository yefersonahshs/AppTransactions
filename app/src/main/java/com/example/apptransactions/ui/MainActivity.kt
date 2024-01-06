package com.example.apptransactions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.apptransactions.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fragmentContainer = findViewById(R.id.fragmentContainer)


        loadAuthorizationForm()
    }

    private fun loadAuthorizationForm() {
        fragmentContainer.removeAllViews()
        val authorizationForm = LayoutInflater.from(this).inflate(R.layout.fragment_authorization, fragmentContainer, false)
        fragmentContainer.addView(authorizationForm)
    }

    private fun loadTransactionList() {
        fragmentContainer.removeAllViews()
        val transactionList = LayoutInflater.from(this).inflate(R.layout.fragment_transaction_list, fragmentContainer, false)
        fragmentContainer.addView(transactionList)
    }

    private fun loadTransactionDetail() {
        fragmentContainer.removeAllViews()
        val transactionDetail = LayoutInflater.from(this).inflate(R.layout.fragment_transaction_detail, fragmentContainer, false)
        fragmentContainer.addView(transactionDetail)
    }

    private fun loadSearchTransaction() {
        fragmentContainer.removeAllViews()
        val searchTransaction = LayoutInflater.from(this).inflate(R.layout.fragment_search_transaction, fragmentContainer, false)
        fragmentContainer.addView(searchTransaction)
    }
}