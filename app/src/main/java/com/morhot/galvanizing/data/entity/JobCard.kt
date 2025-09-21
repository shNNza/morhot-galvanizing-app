package com.morhot.galvanizing.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "job_cards")
data class JobCard(
    @PrimaryKey
    val id: String,
    val clientName: String,
    val clientContactNumber: String?,
    val jobDescription: String,
    val quantity: Int,
    val unitOfMeasure: String,
    val unitPrice: Double,
    val totalAmount: Double,
    val dateCreated: Date,
    val dateCompleted: Date?,
    val status: JobCardStatus,
    val notes: String?,
    val syncedToPastel: Boolean = false,
    val pastelJobId: String?,
    val createdBy: String,
    val lastModified: Date,
    val isDeleted: Boolean = false
)

enum class JobCardStatus {
    DRAFT,
    IN_PROGRESS,
    COMPLETED,
    INVOICED,
    CANCELLED
}