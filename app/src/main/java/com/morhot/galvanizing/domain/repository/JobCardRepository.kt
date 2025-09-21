package com.morhot.galvanizing.domain.repository

import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardStatus
import kotlinx.coroutines.flow.Flow

interface JobCardRepository {
    fun getAllJobCards(): Flow<List<JobCard>>
    suspend fun getJobCardById(id: String): JobCard?
    suspend fun getUnsyncedJobCards(): List<JobCard>
    fun getJobCardsByStatus(status: JobCardStatus): Flow<List<JobCard>>
    suspend fun insertJobCard(jobCard: JobCard)
    suspend fun updateJobCard(jobCard: JobCard)
    suspend fun deleteJobCard(id: String)
    suspend fun markAsSynced(id: String, pastelJobId: String)
    suspend fun getJobCardCount(): Int
}