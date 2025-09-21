package com.morhot.galvanizing.ui.screens.jobcard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.morhot.galvanizing.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCardCreateScreen(
    onNavigateBack: () -> Unit,
    onJobCardCreated: (String) -> Unit,
    viewModel: JobCardCreateViewModel = hiltViewModel()
) {
    var clientName by remember { mutableStateOf("") }
    var clientContactNumber by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unitOfMeasure by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.jobCardCreated) {
        if (uiState.jobCardCreated != null) {
            onJobCardCreated(uiState.jobCardCreated)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_job_card)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = clientName,
                onValueChange = { clientName = it },
                label = { Text(stringResource(R.string.client_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = clientContactNumber,
                onValueChange = { clientContactNumber = it },
                label = { Text("Contact Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            
            OutlinedTextField(
                value = jobDescription,
                onValueChange = { jobDescription = it },
                label = { Text(stringResource(R.string.job_description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text(stringResource(R.string.quantity)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = unitOfMeasure,
                    onValueChange = { unitOfMeasure = it },
                    label = { Text("Unit") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            
            OutlinedTextField(
                value = unitPrice,
                onValueChange = { unitPrice = it },
                label = { Text(stringResource(R.string.unit_price)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                prefix = { Text("R ") }
            )
            
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4
            )
            
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cancel))
                }
                
                Button(
                    onClick = {
                        viewModel.createJobCard(
                            clientName = clientName,
                            clientContactNumber = clientContactNumber.takeIf { it.isNotBlank() },
                            jobDescription = jobDescription,
                            quantity = quantity.toIntOrNull() ?: 0,
                            unitOfMeasure = unitOfMeasure,
                            unitPrice = unitPrice.toDoubleOrNull() ?: 0.0,
                            notes = notes.takeIf { it.isNotBlank() }
                        )
                    },
                    modifier = Modifier.weight(1f),
                    enabled = clientName.isNotBlank() && 
                             jobDescription.isNotBlank() && 
                             quantity.toIntOrNull() != null && 
                             unitOfMeasure.isNotBlank() && 
                             unitPrice.toDoubleOrNull() != null && 
                             !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            size = 16.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}