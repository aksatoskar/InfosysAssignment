package com.example.infosysassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infosysassignment.feature.authentication.view.LoginScreen
import com.example.infosysassignment.common.theme.InfosysAssignmentTheme
import com.example.infosysassignment.feature.authentication.viewmodel.LoginViewModel
import com.example.infosysassignment.feature.rental.view.CreateRideScreen
import com.example.infosysassignment.feature.rental.view.RentalScreen
import com.example.infosysassignment.feature.rental.viewmodel.RentalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val rentalViewModel: RentalViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfosysAssignmentTheme {
                NavigationHost(rentalViewModel)
            }
        }
    }

    @Composable
    fun NavigationHost(rentalViewModel: RentalViewModel) {
        val navController = rememberNavController()
        val loginUIState by loginViewModel.loginUIState.collectAsState()

        Scaffold { innerPadding ->
            NavHost(navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        loginViewModel,
                        navController
                    )
                }
                composable("create_ride") {
                    CreateRideScreen(viewModel = rentalViewModel,
                        navController
                    )
                }
                composable("rental/{source}/{destination}") { backStackEntry ->
                    val source = backStackEntry.arguments?.getString("source")
                    val destination = backStackEntry.arguments?.getString("destination")
                    RentalScreen(
                        source = source ?: "",
                        destination = destination ?: "",
                        viewModel = rentalViewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}