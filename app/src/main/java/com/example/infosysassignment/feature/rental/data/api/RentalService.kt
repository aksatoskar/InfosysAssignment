package com.example.infosysassignment.feature.rental.data.api

import com.example.infosysassignment.feature.rental.data.model.response.CarResponse
import com.example.infosysassignment.feature.rental.data.model.response.RentalResponse

class RentalService: RentalApi {
    override suspend fun createRental(
        customerId: Int,
        source: String,
        destination: String
    ): RentalResponse {
        return RentalResponse(
            carResponse = CarResponse(id = 101, model = "Audi"),
            maxSpeedLimit = 50
        )
    }
}