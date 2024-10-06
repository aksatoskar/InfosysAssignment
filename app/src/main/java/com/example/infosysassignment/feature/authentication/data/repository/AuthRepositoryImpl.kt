package com.example.infosysassignment.feature.authentication.data.repository

import com.example.infosysassignment.feature.authentication.data.api.AuthApi
import com.example.infosysassignment.feature.authentication.data.model.AuthResponse
import com.example.infosysassignment.feature.authentication.domain.repository.AuthRepository
import com.example.infosysassignment.common.model.Customer
import com.example.infosysassignment.common.utils.SharedPreferenceManager

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferenceManager: SharedPreferenceManager
): AuthRepository {
    override suspend fun doLogin(username: String, password: String): Customer {
        val authResponse = authApi.login(username, password)
        val customer = authResponse.mapToDomain()
        saveUser(customer)
        return customer
    }

    private fun AuthResponse.mapToDomain(): Customer {
        return Customer(
            id = this.id,
            name = this.name
        )
    }

    private fun saveUser(customer: Customer) {
        sharedPreferenceManager.clearPreferences()
        sharedPreferenceManager.saveUsername(customer.name)
        sharedPreferenceManager.saveUserId(customer.id)
    }
}