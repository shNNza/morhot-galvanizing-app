package com.morhot.galvanizing.di

import android.content.Context
import androidx.room.Room
import com.morhot.galvanizing.data.dao.JobCardDao
import com.morhot.galvanizing.data.dao.JobCardItemDao
import com.morhot.galvanizing.data.dao.SyncQueueDao
import com.morhot.galvanizing.data.database.MorhotDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MorhotDatabase {
        return Room.databaseBuilder(
            context,
            MorhotDatabase::class.java,
            "morhot_database"
        ).build()
    }
    
    @Provides
    fun provideJobCardDao(database: MorhotDatabase): JobCardDao {
        return database.jobCardDao()
    }
    
    @Provides
    fun provideJobCardItemDao(database: MorhotDatabase): JobCardItemDao {
        return database.jobCardItemDao()
    }
    
    @Provides
    fun provideSyncQueueDao(database: MorhotDatabase): SyncQueueDao {
        return database.syncQueueDao()
    }
}