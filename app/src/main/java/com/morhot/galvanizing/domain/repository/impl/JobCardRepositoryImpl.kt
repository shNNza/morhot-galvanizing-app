package com.morhot.galvanizing.domain.repository.impl

import com.morhot.galvanizing.data.dao.JobCardDao
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardStatus
import com.morhot.galvanizing.domain.repository.JobCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobCardRepositoryImpl @Inject constructor(
    private val jobCardDao: JobCardDao
) : JobCardRepository {
    
    override fun getAllJobCards(): Flow<List<JobCard>> {
        return jobCardDao.getAllJobCards()
    }
    
    override suspend fun getJobCardById(id: String): JobCard? {
        return jobCardDao.getJobCardById(id)
    }
    
    override suspend fun getUnsyncedJobCards(): List<JobCard> {
        return jobCardDao.getUnsyncedJobCards()
    }
    
    override fun getJobCardsByStatus(status: JobCardStatus): Flow<List<JobCard>> {
        return jobCardDao.getJobCardsByStatus(status)
    }
    
    override suspend fun insertJobCard(jobCard: JobCard) {
        jobCardDao.insertJobCard(jobCard)
    }
    
    override suspend fun updateJobCard(jobCard: JobCard) {
        jobCardDao.updateJobCard(jobCard)
    }
    
    override suspend fun deleteJobCard(id: String) {
        jobCardDao.deleteJobCard(id)
    }
    
    override suspend fun markAsSynced(id: String, pastelJobId: String) {
        jobCardDao.markAsSynced(id, pastelJobId)
    }
    
    override suspend fun getJobCardCount(): Int {
        return jobCardDao.getJobCardCount()
    }
}