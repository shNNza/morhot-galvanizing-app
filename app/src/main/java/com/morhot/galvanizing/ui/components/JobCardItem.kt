package com.morhot.galvanizing.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.morhot.galvanizing.data.entity.JobCard
import com.morhot.galvanizing.data.entity.JobCardStatus
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCardItem(
    jobCard: JobCard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = jobCard.clientName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = jobCard.jobDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Text(
                        text = "Quantity: ${jobCard.quantity} ${jobCard.unitOfMeasure}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    
                    Text(
                        text = "Date: ${dateFormat.format(jobCard.dateCreated)}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    StatusChip(status = jobCard.status)
                    
                    Text(
                        text = currencyFormat.format(jobCard.totalAmount),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    if (jobCard.syncedToPastel) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Synced",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Synced",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(status: JobCardStatus) {
    val (icon, color, text) = when (status) {
        JobCardStatus.DRAFT -> Triple(Icons.Default.Schedule, MaterialTheme.colorScheme.outline, "Draft")
        JobCardStatus.IN_PROGRESS -> Triple(Icons.Default.Schedule, MaterialTheme.colorScheme.primary, "In Progress")
        JobCardStatus.COMPLETED -> Triple(Icons.Default.CheckCircle, MaterialTheme.colorScheme.tertiary, "Completed")
        JobCardStatus.INVOICED -> Triple(Icons.Default.CheckCircle, MaterialTheme.colorScheme.secondary, "Invoiced")
        JobCardStatus.CANCELLED -> Triple(Icons.Default.Schedule, MaterialTheme.colorScheme.error, "Cancelled")
    }
    
    AssistChip(
        onClick = { },
        label = { Text(text) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(16.dp)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            leadingIconContentColor = color,
            labelColor = color
        )
    )
}