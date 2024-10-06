package com.example.infosysassignment.feature.rental.data.api

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SpeedService: SpeedApi {
    override suspend fun getCurrentSpeed(): Flow<Int> = flow {
        while (true) {
            val currentSpeed = (0..100).random() // Generate random speed between 0 and 100
            emit(currentSpeed) // Emit the speed value
            delay(5000L) // Wait for 5 seconds before emitting the next value
        }
    }

}