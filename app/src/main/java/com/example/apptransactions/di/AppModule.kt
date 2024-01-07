package com.example.apptransactions.di

import android.content.Context
import androidx.room.Room
import com.example.apptransactions.data.TransactionRepository
import com.example.apptransactions.data.api.ApiService
import com.example.apptransactions.data.local.database.AppDatabase
import com.example.apptransactions.data.local.database.dao.TransactionDao
import com.example.apptransactions.data.local.repository.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
        apiService: ApiService
    ): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao, apiService)
    }
}
