package com.example.infosysassignment.feature.rental.data.api

import com.example.infosysassignment.feature.rental.data.model.response.RentalResponse

interface RentalApi {
    suspend fun createRental(customerId: Int, source: String, destination: String): RentalResponse
}