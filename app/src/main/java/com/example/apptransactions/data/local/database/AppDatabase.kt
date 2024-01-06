package com.example.apptransactions.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apptransactions.data.local.database.dao.TransactionDao
import com.example.apptransactions.data.local.database.entities.TransactionEntity


@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
}