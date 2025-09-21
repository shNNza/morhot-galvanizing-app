package com.morhot.galvanizing.network

import com.morhot.galvanizing.network.dto.PastelJobRequest
import com.morhot.galvanizing.network.dto.PastelJobResponse
import com.morhot.galvanizing.network.dto.PastelAuthRequest
import com.morhot.galvanizing.network.dto.PastelAuthResponse
import retrofit2.Response
import retrofit2.http.*

interface PastelApiService {
    
    @POST("auth/login")
    suspend fun authenticate(
        @Body authRequest: PastelAuthRequest
    ): Response<PastelAuthResponse>
    
    @POST("jobs")
    suspend fun createJob(
        @Header("Authorization") token: String,
        @Body jobRequest: PastelJobRequest
    ): Response<PastelJobResponse>
    
    @PUT("jobs/{id}")
    suspend fun updateJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String,
        @Body jobRequest: PastelJobRequest
    ): Response<PastelJobResponse>
    
    @GET("jobs/{id}")
    suspend fun getJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String
    ): Response<PastelJobResponse>
    
    @DELETE("jobs/{id}")
    suspend fun deleteJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String
    ): Response<Unit>
    
    @GET("jobs")
    suspend fun getAllJobs(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): Response<List<PastelJobResponse>>
}