package com.example.infosysassignment.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infosysassignment.view.viewmodel.RentalViewModel

@Composable
fun RentalScreen(
    viewModel: RentalViewModel,
    innerPadding: PaddingValues, ) {
    val rentalUIState by viewModel.rentalUIState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(16.dp), // Add padding to the column
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Max Speed Limit: ${rentalUIState.maxSpeedLimit} km/h",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Current Speed: ${rentalUIState.currentSpeed ?: "N/A"} km/h",
            style = MaterialTheme.typography.bodyLarge
        )

        if (rentalUIState.isSpeedLimitExceeded && rentalUIState.isAlertShown) {
            AlertDialog(
                onDismissRequest = { /* Do nothing */ },
                title = { Text("Speed Limit Exceeded") },
                text = { Text("You are driving above the max speed limit of ${rentalUIState.maxSpeedLimit} km/h!") },
                confirmButton = {
                    Button(onClick = { viewModel.dismissAlert() }) {
                        Text("OK")
                    }
                }
            )
        }

    }
}