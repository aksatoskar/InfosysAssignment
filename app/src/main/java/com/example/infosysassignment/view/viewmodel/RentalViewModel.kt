package com.example.infosysassignment.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infosysassignment.feature.rental.data.api.SpeedApi
import com.example.infosysassignment.feature.rental.domain.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class RentalUIState(
    val maxSpeedLimit: Int = 0,
    val currentSpeed: Int? = null,
    val isSpeedLimitExceeded:Boolean = false,
    val isAlertShown: Boolean = false
)

class RentalViewModel(
    private val repository: RentalRepository,
    private val speedService: SpeedApi
) : ViewModel() {

    companion object {
        const val TAG = "RENTAL_VIEWMODEL"
    }

    private val _rentalUIState: MutableStateFlow<RentalUIState> =
        MutableStateFlow(RentalUIState())
    val rentalUIState = _rentalUIState.asStateFlow()

    fun createRental() {
        viewModelScope.launch {
            try {
                val response = repository.createRental(1, "Mumbai", "Pune")
                _rentalUIState.value =
                    _rentalUIState.value.copy(maxSpeedLimit = response.maxSpeedLimit)

                startSpeedService()

            } catch (e: Exception) {
                Log.e(TAG, "Problems getting max speed limit")
            }
        }
    }

    private fun startSpeedService() {
        viewModelScope.launch {
            speedService.getCurrentSpeed().collectLatest { currentSpeed ->
                _rentalUIState.value =
                    _rentalUIState.value.copy(currentSpeed = currentSpeed) // Update the speed in the state

                checkSpeedLimit()
            }
        }
    }

    private fun checkSpeedLimit() {
        val maxSpeedLimit = _rentalUIState.value.maxSpeedLimit
        if (_rentalUIState.value.currentSpeed != null && _rentalUIState.value.currentSpeed!! > maxSpeedLimit) {
            if (!_rentalUIState.value.isAlertShown) { // Only show the alert if it hasn't been shown yet
                _rentalUIState.value = _rentalUIState.value.copy(isSpeedLimitExceeded = true, isAlertShown = true) // Set the flag to true so the alert is not shown again
            }
        } else {
            _rentalUIState.value =
                _rentalUIState.value.copy(isSpeedLimitExceeded = false, isAlertShown = false)
        }
    }

    fun dismissAlert() {
        _rentalUIState.value =
            _rentalUIState.value.copy(isAlertShown = false)
    }
}