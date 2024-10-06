package com.example.infosysassignment.feature.rental.domain.repository

import com.example.infosysassignment.common.model.Customer
import com.example.infosysassignment.feature.rental.domain.model.Rental

interface RentalRepository {

    suspend fun createRental(customerId: Int, source: String, destination: String): Rental

    suspend fun getUser(): Customer
}