package com.example.apptransactions.data.local.database.entities

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)
