package com.morhot.galvanizing.ui.screens.jobcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.morhot.galvanizing.R
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.ui.components.JobCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCardListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: JobCardListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.morhot_logo),
                            contentDescription = "Morhot Logo",
                            modifier = Modifier.height(24.dp)
                        )
                        Text(stringResource(R.string.job_cards))
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.syncData() }) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = stringResource(R.string.sync_data)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.new_job_card)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.jobCards.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No job cards found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.jobCards) { jobCard ->
                        JobCardItem(
                            jobCard = jobCard,
                            onClick = { onNavigateToDetail(jobCard.id) }
                        )
                    }
                }
            }
            
            if (uiState.errorMessage != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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
    }
}