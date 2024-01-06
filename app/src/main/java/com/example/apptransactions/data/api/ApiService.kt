package com.example.apptransactions.data.api

import com.example.apptransactions.data.model.CancelRequest
import com.example.apptransactions.data.model.TransactionModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("authorization")
    suspend fun authorizeTransaction(@Body transaction: TransactionModel): TransactionModel

    @POST("annulation")
    suspend fun cancelTransaction(@Body cancelRequest: CancelRequest): TransactionModel


}