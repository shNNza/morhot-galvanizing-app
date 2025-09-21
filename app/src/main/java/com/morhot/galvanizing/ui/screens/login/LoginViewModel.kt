package com.morhot.galvanizing.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // TODO: Implement actual authentication logic
                // For now, simulate login with any non-empty credentials
                if (username.isNotBlank() && password.isNotBlank()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Please enter valid credentials"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Login failed: ${e.message}"
                )
            }
        }
    }
}