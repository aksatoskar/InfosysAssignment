package com.example.infosysassignment.feature.rental.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infosysassignment.common.model.Customer
import com.example.infosysassignment.feature.rental.data.api.SpeedApi
import com.example.infosysassignment.feature.rental.domain.model.Car
import com.example.infosysassignment.feature.rental.domain.repository.RentalRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class RentalUIState(
    val maxSpeedLimit: Int = 0,
    val car: Car? = null,
    val currentSpeed: Int? = null,
    val isSpeedLimitExceeded:Boolean = false,
    val isAlertShown: Boolean = false,
    val isRentalSuccessful: Boolean = false,
    val customer: Customer? = null
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

    private var speedServiceJob: Job? = null // Job to manage speed service coroutine
    private var isSpeedServiceRunning = false // Track speed service state

    fun createRental(source: String, destination: String) {
        viewModelScope.launch {
            try {
                val customerDetails = if(_rentalUIState.value.customer == null) {
                    repository.getUser()
                } else {
                    _rentalUIState.value.customer
                }

                val response = repository.createRental(customerDetails!!.id, source, destination)
                _rentalUIState.value =
                    _rentalUIState.value.copy(isRentalSuccessful = true, car = response.car, maxSpeedLimit = response.maxSpeedLimit)
            } catch (e: Exception) {
                _rentalUIState.value =
                    _rentalUIState.value.copy(isRentalSuccessful = false)
                Log.e(TAG, "Problems getting max speed limit")
            }
        }
    }

    fun toggleSpeedService() {
        if (isSpeedServiceRunning) {
            stopSpeedService()
        } else {
            startSpeedService()
        }
    }

    private fun startSpeedService() {
        if (!isSpeedServiceRunning) {
            isSpeedServiceRunning = true
            speedServiceJob = viewModelScope.launch {
                speedService.getCurrentSpeed().collectLatest { currentSpeed ->
                    _rentalUIState.value =
                        _rentalUIState.value.copy(currentSpeed = currentSpeed)

                    checkSpeedLimit()
                }
            }
        }
    }

    private fun stopSpeedService() {
        speedServiceJob?.cancel() // Cancel the job to stop the speed service
        speedServiceJob = null // Reset the job
        isSpeedServiceRunning = false // Update the state
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

    fun getUser() {
        viewModelScope.launch {
            val customer = repository.getUser()

            _rentalUIState.value = _rentalUIState.value.copy(
                customer = customer
            )
        }
    }

    fun dismissAlert() {
        _rentalUIState.value =
            _rentalUIState.value.copy(isAlertShown = false)
    }

    fun resetRentalStatus() {
        _rentalUIState.value = _rentalUIState.value.copy(isRentalSuccessful = false)
    }
}