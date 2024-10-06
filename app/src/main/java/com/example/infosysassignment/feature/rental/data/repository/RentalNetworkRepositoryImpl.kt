package com.example.infosysassignment.feature.rental.data.repository

import com.example.infosysassignment.common.model.Customer
import com.example.infosysassignment.common.utils.SharedPreferenceManager
import com.example.infosysassignment.feature.rental.data.api.RentalApi
import com.example.infosysassignment.feature.rental.data.model.response.CarResponse
import com.example.infosysassignment.feature.rental.data.model.response.RentalResponse
import com.example.infosysassignment.feature.rental.domain.model.Car
import com.example.infosysassignment.feature.rental.domain.repository.RentalRepository
import com.example.infosysassignment.feature.rental.domain.model.Rental

class RentalNetworkRepositoryImpl(
    private val rentalApi: RentalApi,
    private val sharedPreferenceManager: SharedPreferenceManager
): RentalRepository {
    override suspend fun createRental(customerId: Int, source: String, destination: String): Rental {
        val rentalResponse = rentalApi.createRental(customerId, source, destination)
        return rentalResponse.mapToDomain()
    }

    override suspend fun getUser(): Customer {
        val id = sharedPreferenceManager.getUserId() ?: 0
        val name = sharedPreferenceManager.getUsername()
        return Customer(id, name ?: "")
    }

    private fun RentalResponse.mapToDomain(): Rental {
        return Rental(
            car = this.carResponse.mapToDomain(),
            maxSpeedLimit = this.maxSpeedLimit
        )
    }

    private fun CarResponse.mapToDomain(): Car {
        return Car(
           id = this.id,
           model = this.model
        )
    }
}