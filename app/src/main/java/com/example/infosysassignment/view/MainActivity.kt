package com.example.infosysassignment.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.example.infosysassignment.view.theme.InfosysAssignmentTheme
import com.example.infosysassignment.view.viewmodel.RentalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val rentalViewModel: RentalViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rentalViewModel.createRental()
        enableEdgeToEdge()
        setContent {
            InfosysAssignmentTheme {
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
                    RentalScreen(viewModel = rentalViewModel, innerPadding)
                }
            }
        }
    }
}