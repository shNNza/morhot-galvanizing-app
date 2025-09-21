package com.morhot.galvanizing.data.dao

import androidx.room.*
import com.morhot.galvanizing.data.entity.JobCardItem
import kotlinx.coroutines.flow.Flow

@Dao
interface JobCardItemDao {
    
    @Query("SELECT * FROM job_card_items WHERE jobCardId = :jobCardId ORDER BY sortOrder ASC")
    fun getJobCardItems(jobCardId: String): Flow<List<JobCardItem>>
    
    @Query("SELECT * FROM job_card_items WHERE id = :id")
    suspend fun getJobCardItemById(id: String): JobCardItem?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobCardItem(jobCardItem: JobCardItem)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobCardItems(jobCardItems: List<JobCardItem>)
    
    @Update
    suspend fun updateJobCardItem(jobCardItem: JobCardItem)
    
    @Delete
    suspend fun deleteJobCardItem(jobCardItem: JobCardItem)
    
    @Query("DELETE FROM job_card_items WHERE jobCardId = :jobCardId")
    suspend fun deleteJobCardItems(jobCardId: String)
}