package com.example.apptransactions.data.model

import com.google.gson.annotations.SerializedName

data class CancelRequest(
    @SerializedName("receiptId")
    val receiptId: String,

    @SerializedName("rrn")
    val rrn: String
)