package com.example.infosysassignment.feature.rental.data.api

import kotlinx.coroutines.flow.Flow

interface SpeedApi {
    suspend fun getCurrentSpeed(): Flow<Int>
}