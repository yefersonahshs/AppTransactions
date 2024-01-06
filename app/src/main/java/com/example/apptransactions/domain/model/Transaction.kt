package com.example.apptransactions.domain.model

data class Transaction(
    val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)