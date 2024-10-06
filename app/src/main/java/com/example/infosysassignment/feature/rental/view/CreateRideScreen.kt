package com.example.infosysassignment.feature.rental.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infosysassignment.feature.rental.viewmodel.RentalViewModel

@Composable
fun CreateRideScreen(
    viewModel: RentalViewModel,
    navController: NavController
) {
    val rentalUIState by viewModel.rentalUIState.collectAsState()

    var source by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    if (rentalUIState.isRentalSuccessful) {
        LaunchedEffect(Unit) {
            viewModel.resetRentalStatus()
            navController.navigate("rental/$source/$destination")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Ride", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        rentalUIState.customer?.name.let {
            Text(text = "Hello, $it", style = MaterialTheme.typography.headlineMedium)
        }

        OutlinedTextField(
            value = source,
            onValueChange = { source = it },
            label = { Text("Source") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.createRental(source, destination)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create Ride")
        }
    }
}