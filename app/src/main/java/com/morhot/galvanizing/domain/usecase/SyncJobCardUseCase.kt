package com.morhot.galvanizing.domain.usecase

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.morhot.galvanizing.data.dao.SyncQueueDao
import com.morhot.galvanizing.data.entity.SyncOperation
import com.morhot.galvanizing.data.entity.SyncQueue
import com.morhot.galvanizing.workers.SyncWorker
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncJobCardUseCase @Inject constructor(
    private val syncQueueDao: SyncQueueDao,
    private val workManager: WorkManager
) {
    
    suspend fun queueJobCardForSync(
        jobCardId: String,
        operation: SyncOperation,
        priority: Int = 0
    ) {
        val syncItem = SyncQueue(
            id = UUID.randomUUID().toString(),
            entityType = "JobCard",
            entityId = jobCardId,
            operation = operation,
            retryCount = 0,
            lastAttempt = null,
            errorMessage = null,
            createdAt = Date(),
            priority = priority
        )
        
        syncQueueDao.insertSyncItem(syncItem)
        
        // Schedule background sync work
        scheduleSync()
    }
    
    fun scheduleSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        
        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()
        
        workManager.enqueue(syncWorkRequest)
    }
    
    suspend fun clearFailedSyncItems() {
        val failedItems = syncQueueDao.getAllPendingSync().filter { it.retryCount >= 3 }
        failedItems.forEach { syncQueueDao.deleteSyncItem(it) }
    }
}