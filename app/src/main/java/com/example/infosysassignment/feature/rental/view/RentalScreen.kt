package com.example.infosysassignment.feature.rental.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infosysassignment.feature.rental.viewmodel.RentalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalScreen(
    viewModel: RentalViewModel,
    innerPadding: PaddingValues,
    source: String,
    destination: String
) {

    Scaffold(
        topBar = {
            // Optional Top App Bar
            CenterAlignedTopAppBar(
                title = { Text("Car Rental App") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        RentalScreenContent(viewModel = viewModel, source, destination, innerPadding)
    }
}

@Composable
fun RentalScreenContent(
    viewModel: RentalViewModel, source: String,
    destination: String, innerPadding: PaddingValues
) {
    val rentalUIState by viewModel.rentalUIState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp), // Add padding to the column
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Space between source and destination
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Source: $source",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Destination: $destination",
                style = MaterialTheme.typography.titleMedium
            )
        }

        rentalUIState.car?.model.let {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Car Name: $it", // Assuming Audi is the car name
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Max Speed Limit: ${rentalUIState.maxSpeedLimit} km/h",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Current Speed: ${rentalUIState.currentSpeed ?: "N/A"} km/h",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.toggleSpeedService() }) {
            Text(if (rentalUIState.currentSpeed != null) "Stop Driving" else "Start Driving")
        }

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