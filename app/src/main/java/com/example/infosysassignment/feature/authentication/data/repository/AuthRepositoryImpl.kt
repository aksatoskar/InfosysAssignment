package com.example.infosysassignment.feature.authentication.data.repository

import com.example.infosysassignment.feature.authentication.data.api.AuthApi
import com.example.infosysassignment.feature.authentication.data.model.AuthResponse
import com.example.infosysassignment.feature.authentication.domain.repository.AuthRepository
import com.example.infosysassignment.common.model.Customer

class AuthRepositoryImpl(private val authApi: AuthApi): AuthRepository {
    override suspend fun doLogin(username: String, password: String): Customer {
        val authResponse = authApi.login(username, password)
        val customer = authResponse.mapToDomain()
        return customer
    }

    private fun AuthResponse.mapToDomain(): Customer {
        return Customer(
            id = this.id,
            name = this.name
        )
    }
}