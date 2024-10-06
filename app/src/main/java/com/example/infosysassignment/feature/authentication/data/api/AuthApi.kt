package com.example.infosysassignment.feature.authentication.data.api

import com.example.infosysassignment.feature.authentication.data.model.AuthResponse

interface AuthApi {
    suspend fun login(userName: String, password: String): AuthResponse
}