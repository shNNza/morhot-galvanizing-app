package com.morhot.galvanizing.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "job_card_items",
    foreignKeys = [
        ForeignKey(
            entity = JobCard::class,
            parentColumns = ["id"],
            childColumns = ["jobCardId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JobCardItem(
    @PrimaryKey
    val id: String,
    val jobCardId: String,
    val itemDescription: String,
    val quantity: Int,
    val unitOfMeasure: String,
    val unitPrice: Double,
    val lineTotal: Double,
    val sortOrder: Int
)