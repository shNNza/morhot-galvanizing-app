package com.morhot.galvanizing.ui.screens.jobcard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCardDetailScreen(
    jobCardId: String,
    onNavigateBack: () -> Unit,
    viewModel: JobCardDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(jobCardId) {
        viewModel.loadJobCard(jobCardId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Card Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Edit functionality */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = { viewModel.syncToPastel() }) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync to Pastel"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.jobCard != null) {
            val jobCard = uiState.jobCard
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Client Information",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        DetailRow("Client Name", jobCard.clientName)
                        jobCard.clientContactNumber?.let {
                            DetailRow("Contact Number", it)
                        }
                    }
                }
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Job Details",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        DetailRow("Description", jobCard.jobDescription)
                        DetailRow("Quantity", "${jobCard.quantity} ${jobCard.unitOfMeasure}")
                        DetailRow("Unit Price", currencyFormat.format(jobCard.unitPrice))
                        DetailRow("Total Amount", currencyFormat.format(jobCard.totalAmount))
                        DetailRow("Status", jobCard.status.name)
                        DetailRow("Created", dateFormat.format(jobCard.dateCreated))
                        jobCard.dateCompleted?.let {
                            DetailRow("Completed", dateFormat.format(it))
                        }
                        jobCard.notes?.let {
                            DetailRow("Notes", it)
                        }
                    }
                }
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Sync Status",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        DetailRow("Synced to Pastel", if (jobCard.syncedToPastel) "Yes" else "No")
                        jobCard.pastelJobId?.let {
                            DetailRow("Pastel Job ID", it)
                        }
                    }
                }
                
                if (uiState.errorMessage != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Job card not found")
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
    }
}