package com.morhot.galvanizing.ui.screens.jobcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.domain.repository.JobCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JobCardDetailUiState(
    val jobCard: JobCard? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class JobCardDetailViewModel @Inject constructor(
    private val jobCardRepository: JobCardRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(JobCardDetailUiState())
    val uiState: StateFlow<JobCardDetailUiState> = _uiState.asStateFlow()
    
    fun loadJobCard(jobCardId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val jobCard = jobCardRepository.getJobCardById(jobCardId)
                _uiState.value = _uiState.value.copy(
                    jobCard = jobCard,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load job card: ${e.message}"
                )
            }
        }
    }
    
    fun syncToPastel() {
        viewModelScope.launch {
            try {
                val jobCard = _uiState.value.jobCard
                if (jobCard != null && !jobCard.syncedToPastel) {
                    // TODO: Implement actual sync to Pastel
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Sync to Pastel functionality will be implemented"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Job card is already synced or not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Sync failed: ${e.message}"
                )
            }
        }
    }
}