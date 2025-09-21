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

data class JobCardListUiState(
    val jobCards: List<JobCard> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class JobCardListViewModel @Inject constructor(
    private val jobCardRepository: JobCardRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(JobCardListUiState())
    val uiState: StateFlow<JobCardListUiState> = _uiState.asStateFlow()
    
    init {
        loadJobCards()
    }
    
    private fun loadJobCards() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                jobCardRepository.getAllJobCards().collect { jobCards ->
                    _uiState.value = _uiState.value.copy(
                        jobCards = jobCards,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load job cards: ${e.message}"
                )
            }
        }
    }
    
    fun syncData() {
        viewModelScope.launch {
            try {
                // TODO: Implement sync with Pastel
                _uiState.value = _uiState.value.copy(errorMessage = "Sync functionality will be implemented")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Sync failed: ${e.message}"
                )
            }
        }
    }
}