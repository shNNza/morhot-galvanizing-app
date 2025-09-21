package com.morhot.galvanizing.data.dao

import androidx.room.*
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface JobCardDao {
    
    @Query("SELECT * FROM job_cards WHERE isDeleted = 0 ORDER BY dateCreated DESC")
    fun getAllJobCards(): Flow<List<JobCard>>
    
    @Query("SELECT * FROM job_cards WHERE id = :id AND isDeleted = 0")
    suspend fun getJobCardById(id: String): JobCard?
    
    @Query("SELECT * FROM job_cards WHERE syncedToPastel = 0 AND isDeleted = 0")
    suspend fun getUnsyncedJobCards(): List<JobCard>
    
    @Query("SELECT * FROM job_cards WHERE status = :status AND isDeleted = 0")
    fun getJobCardsByStatus(status: JobCardStatus): Flow<List<JobCard>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobCard(jobCard: JobCard)
    
    @Update
    suspend fun updateJobCard(jobCard: JobCard)
    
    @Query("UPDATE job_cards SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteJobCard(id: String)
    
    @Query("UPDATE job_cards SET syncedToPastel = 1, pastelJobId = :pastelJobId WHERE id = :id")
    suspend fun markAsSynced(id: String, pastelJobId: String)
    
    @Query("SELECT COUNT(*) FROM job_cards WHERE isDeleted = 0")
    suspend fun getJobCardCount(): Int
}