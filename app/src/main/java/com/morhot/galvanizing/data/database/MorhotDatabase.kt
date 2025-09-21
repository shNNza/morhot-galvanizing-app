package com.morhot.galvanizing.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.morhot.galvanizing.data.dao.JobCardDao
import com.morhot.galvanizing.data.dao.JobCardItemDao
import com.morhot.galvanizing.data.dao.SyncQueueDao
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardItem
import com.morhot.galvanizing.data.entity.SyncQueue

@Database(
    entities = [JobCard::class, JobCardItem::class, SyncQueue::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MorhotDatabase : RoomDatabase() {
    
    abstract fun jobCardDao(): JobCardDao
    abstract fun jobCardItemDao(): JobCardItemDao
    abstract fun syncQueueDao(): SyncQueueDao
    
    companion object {
        @Volatile
        private var INSTANCE: MorhotDatabase? = null
        
        fun getDatabase(context: Context): MorhotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MorhotDatabase::class.java,
                    "morhot_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}