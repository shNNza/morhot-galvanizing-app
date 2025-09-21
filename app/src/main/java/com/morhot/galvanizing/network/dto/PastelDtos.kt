package com.morhot.galvanizing.network.dto

import com.google.gson.annotations.SerializedName

data class PastelAuthRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("database")
    val database: String? = null
)

data class PastelAuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("user_id")
    val userId: String
)

data class PastelJobRequest(
    @SerializedName("customer_code")
    val customerCode: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("job_description")
    val jobDescription: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("unit_of_measure")
    val unitOfMeasure: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    @SerializedName("total_amount")
    val totalAmount: Double,
    @SerializedName("job_date")
    val jobDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("external_reference")
    val externalReference: String
)

data class PastelJobResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("job_number")
    val jobNumber: String,
    @SerializedName("customer_code")
    val customerCode: String,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("job_description")
    val jobDescription: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("unit_of_measure")
    val unitOfMeasure: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    @SerializedName("total_amount")
    val totalAmount: Double,
    @SerializedName("job_date")
    val jobDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("external_reference")
    val externalReference: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class PastelErrorResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: Int
)