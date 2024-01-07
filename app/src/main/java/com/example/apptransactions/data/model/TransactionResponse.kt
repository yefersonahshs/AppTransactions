package com.example.apptransactions.data.model

import com.google.gson.annotations.SerializedName


data class TransactionResponse(
    @SerializedName("receiptId")
    val receiptId: String,

    @SerializedName("rrn")
    val rrn: String,

    @SerializedName("statusCode")
    val statusCode: String,

    @SerializedName("statusDescription")
    val statusDescription: String
)