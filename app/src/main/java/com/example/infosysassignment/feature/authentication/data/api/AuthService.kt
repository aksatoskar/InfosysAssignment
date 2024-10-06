package com.example.infosysassignment.feature.authentication.data.api

import com.example.infosysassignment.feature.authentication.data.model.AuthResponse

class AuthService: AuthApi {
    override suspend fun login(userName: String, password: String): AuthResponse {
        return AuthResponse(id = 1, name = "Akshay")
    }
}