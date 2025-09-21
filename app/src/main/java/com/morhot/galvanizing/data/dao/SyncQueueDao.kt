package com.morhot.galvanizing.data.dao

import androidx.room.*
import com.morhot.galvanizing.data.entity.SyncQueue
import com.morhot.galvanizing.data.entity.SyncOperation

@Dao
interface SyncQueueDao {
    
    @Query("SELECT * FROM sync_queue ORDER BY priority DESC, createdAt ASC")
    suspend fun getAllPendingSync(): List<SyncQueue>
    
    @Query("SELECT * FROM sync_queue WHERE entityType = :entityType AND entityId = :entityId")
    suspend fun getSyncQueueItem(entityType: String, entityId: String): SyncQueue?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncItem(syncItem: SyncQueue)
    
    @Update
    suspend fun updateSyncItem(syncItem: SyncQueue)
    
    @Delete
    suspend fun deleteSyncItem(syncItem: SyncQueue)
    
    @Query("DELETE FROM sync_queue WHERE entityType = :entityType AND entityId = :entityId")
    suspend fun deleteSyncItemByEntity(entityType: String, entityId: String)
    
    @Query("UPDATE sync_queue SET retryCount = retryCount + 1, lastAttempt = :lastAttempt, errorMessage = :errorMessage WHERE id = :id")
    suspend fun incrementRetryCount(id: String, lastAttempt: Long, errorMessage: String?)
}