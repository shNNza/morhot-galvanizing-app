package com.morhot.galvanizing.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sync_queue")
data class SyncQueue(
    @PrimaryKey
    val id: String,
    val entityType: String,
    val entityId: String,
    val operation: SyncOperation,
    val retryCount: Int = 0,
    val lastAttempt: Date?,
    val errorMessage: String?,
    val createdAt: Date,
    val priority: Int = 0
)

enum class SyncOperation {
    CREATE,
    UPDATE,
    DELETE
}