package com.morhot.galvanizing.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.morhot.galvanizing.data.dao.SyncQueueDao
import com.morhot.galvanizing.data.entity.SyncOperation
import com.morhot.galvanizing.domain.repository.JobCardRepository
import com.morhot.galvanizing.network.PastelApiService
import com.morhot.galvanizing.network.dto.PastelJobRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncQueueDao: SyncQueueDao,
    private val jobCardRepository: JobCardRepository,
    private val pastelApiService: PastelApiService
) : CoroutineWorker(appContext, workerParams) {
    
    override suspend fun doWork(): Result {
        return try {
            val pendingSyncItems = syncQueueDao.getAllPendingSync()
            var hasErrors = false
            
            for (syncItem in pendingSyncItems) {
                try {
                    when (syncItem.entityType) {
                        "JobCard" -> {
                            when (syncItem.operation) {
                                SyncOperation.CREATE -> syncCreateJobCard(syncItem.entityId)
                                SyncOperation.UPDATE -> syncUpdateJobCard(syncItem.entityId)
                                SyncOperation.DELETE -> syncDeleteJobCard(syncItem.entityId)
                            }
                            // Remove from sync queue on success
                            syncQueueDao.deleteSyncItem(syncItem)
                        }
                    }
                } catch (e: Exception) {
                    hasErrors = true
                    // Update retry count and error message
                    syncQueueDao.incrementRetryCount(
                        syncItem.id,
                        System.currentTimeMillis(),
                        e.message
                    )
                }
            }
            
            if (hasErrors) Result.retry() else Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
    
    private suspend fun syncCreateJobCard(jobCardId: String) {
        val jobCard = jobCardRepository.getJobCardById(jobCardId)
            ?: throw Exception("Job card not found: $jobCardId")
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        
        val request = PastelJobRequest(
            customerCode = generateCustomerCode(jobCard.clientName),
            customerName = jobCard.clientName,
            jobDescription = jobCard.jobDescription,
            quantity = jobCard.quantity,
            unitOfMeasure = jobCard.unitOfMeasure,
            unitPrice = jobCard.unitPrice,
            totalAmount = jobCard.totalAmount,
            jobDate = dateFormat.format(jobCard.dateCreated),
            status = jobCard.status.name,
            notes = jobCard.notes,
            externalReference = jobCard.id
        )
        
        // TODO: Get auth token from secure storage
        val token = "Bearer YOUR_AUTH_TOKEN"
        
        val response = pastelApiService.createJob(token, request)
        if (response.isSuccessful) {
            val pastelJob = response.body()!!
            jobCardRepository.markAsSynced(jobCard.id, pastelJob.id)
        } else {
            throw Exception("Failed to create job in Pastel: ${response.message()}")
        }
    }
    
    private suspend fun syncUpdateJobCard(jobCardId: String) {
        val jobCard = jobCardRepository.getJobCardById(jobCardId)
            ?: throw Exception("Job card not found: $jobCardId")
        
        if (jobCard.pastelJobId == null) {
            throw Exception("Job card not synced to Pastel yet")
        }
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        
        val request = PastelJobRequest(
            customerCode = generateCustomerCode(jobCard.clientName),
            customerName = jobCard.clientName,
            jobDescription = jobCard.jobDescription,
            quantity = jobCard.quantity,
            unitOfMeasure = jobCard.unitOfMeasure,
            unitPrice = jobCard.unitPrice,
            totalAmount = jobCard.totalAmount,
            jobDate = dateFormat.format(jobCard.dateCreated),
            status = jobCard.status.name,
            notes = jobCard.notes,
            externalReference = jobCard.id
        )
        
        // TODO: Get auth token from secure storage
        val token = "Bearer YOUR_AUTH_TOKEN"
        
        val response = pastelApiService.updateJob(token, jobCard.pastelJobId, request)
        if (!response.isSuccessful) {
            throw Exception("Failed to update job in Pastel: ${response.message()}")
        }
    }
    
    private suspend fun syncDeleteJobCard(jobCardId: String) {
        val jobCard = jobCardRepository.getJobCardById(jobCardId)
        
        if (jobCard?.pastelJobId != null) {
            // TODO: Get auth token from secure storage
            val token = "Bearer YOUR_AUTH_TOKEN"
            
            val response = pastelApiService.deleteJob(token, jobCard.pastelJobId)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete job in Pastel: ${response.message()}")
            }
        }
    }
    
    private fun generateCustomerCode(customerName: String): String {
        // Simple customer code generation - you may want to improve this
        return customerName.take(3).uppercase() + System.currentTimeMillis().toString().takeLast(4)
    }
}