package com.example.apptransactions.data.local.database.dao

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE receiptId = :receiptId")
    suspend fun getTransactionByReceiptId(receiptId: String): TransactionEntity?

}
