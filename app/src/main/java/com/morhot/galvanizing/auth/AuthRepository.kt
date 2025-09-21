package com.morhot.galvanizing.auth

import com.morhot.galvanizing.network.PastelApiService
import com.morhot.galvanizing.network.dto.PastelAuthRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val pastelApiService: PastelApiService,
    private val authManager: AuthManager
) {
    
    suspend fun login(username: String, password: String, database: String? = null): Result<Unit> {
        return try {
            val authRequest = PastelAuthRequest(
                username = username,
                password = password,
                database = database
            )
            
            val response = pastelApiService.authenticate(authRequest)
            
            if (response.isSuccessful) {
                val authResponse = response.body()!!
                authManager.saveAuthToken(
                    accessToken = authResponse.accessToken,
                    refreshToken = authResponse.refreshToken,
                    userId = authResponse.userId,
                    username = username,
                    expiresIn = authResponse.expiresIn
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun logout() {
        authManager.clearAuthData()
    }
    
    fun isLoggedIn(): Boolean {
        return authManager.isLoggedIn() && !authManager.isTokenExpired()
    }
    
    fun getCurrentUser(): String? {
        return authManager.getUsername()
    }
    
    fun getBearerToken(): String? {
        return authManager.getBearerToken()
    }
}