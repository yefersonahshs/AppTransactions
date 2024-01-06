package com.example.apptransactions.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apptransactions.data.local.database.entities.TransactionEntity

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE receiptId = :receiptId LIMIT 1")
    suspend fun getTransactionByReceiptId(receiptId: String): TransactionEntity?
}
