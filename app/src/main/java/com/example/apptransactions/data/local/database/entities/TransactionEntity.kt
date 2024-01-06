package com.example.apptransactions.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val receiptId: String,
    val rrn: String,
    var statusCode: String,
    var statusDescription: String
)
