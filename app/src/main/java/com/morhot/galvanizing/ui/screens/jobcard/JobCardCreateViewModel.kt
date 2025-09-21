package com.morhot.galvanizing.ui.screens.jobcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardStatus
import com.morhot.galvanizing.domain.repository.JobCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

data class JobCardCreateUiState(
    val isLoading: Boolean = false,
    val jobCardCreated: String? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class JobCardCreateViewModel @Inject constructor(
    private val jobCardRepository: JobCardRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(JobCardCreateUiState())
    val uiState: StateFlow<JobCardCreateUiState> = _uiState.asStateFlow()
    
    fun createJobCard(
        clientName: String,
        clientContactNumber: String?,
        jobDescription: String,
        quantity: Int,
        unitOfMeasure: String,
        unitPrice: Double,
        notes: String?
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                val jobCard = JobCard(
                    id = UUID.randomUUID().toString(),
                    clientName = clientName,
                    clientContactNumber = clientContactNumber,
                    jobDescription = jobDescription,
                    quantity = quantity,
                    unitOfMeasure = unitOfMeasure,
                    unitPrice = unitPrice,
                    totalAmount = quantity * unitPrice,
                    dateCreated = Date(),
                    dateCompleted = null,
                    status = JobCardStatus.DRAFT,
                    notes = notes,
                    syncedToPastel = false,
                    pastelJobId = null,
                    createdBy = "current_user", // TODO: Get from authenticated user
                    lastModified = Date(),
                    isDeleted = false
                )
                
                jobCardRepository.insertJobCard(jobCard)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    jobCardCreated = jobCard.id
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to create job card: ${e.message}"
                )
            }
        }
    }
}