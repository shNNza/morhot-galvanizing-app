package com.morhot.galvanizing.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val encryptedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_TOKEN_EXPIRY = "token_expiry"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
    
    fun saveAuthToken(
        accessToken: String,
        refreshToken: String?,
        userId: String,
        username: String,
        expiresIn: Long
    ) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        encryptedPrefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .putString(KEY_USER_ID, userId)
            .putString(KEY_USERNAME, username)
            .putLong(KEY_TOKEN_EXPIRY, expiryTime)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }
    
    fun getAccessToken(): String? {
        return encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }
    
    fun getRefreshToken(): String? {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }
    
    fun getUserId(): String? {
        return encryptedPrefs.getString(KEY_USER_ID, null)
    }
    
    fun getUsername(): String? {
        return encryptedPrefs.getString(KEY_USERNAME, null)
    }
    
    fun isLoggedIn(): Boolean {
        return encryptedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    
    fun isTokenExpired(): Boolean {
        val expiryTime = encryptedPrefs.getLong(KEY_TOKEN_EXPIRY, 0)
        return System.currentTimeMillis() >= expiryTime
    }
    
    fun clearAuthData() {
        encryptedPrefs.edit()
            .clear()
            .apply()
    }
    
    fun getBearerToken(): String? {
        val token = getAccessToken()
        return if (token != null && !isTokenExpired()) {
            "Bearer $token"
        } else {
            null
        }
    }
}