package com.example.infosysassignment.feature.authentication.domain.repository

import com.example.infosysassignment.common.model.Customer

interface AuthRepository {

    suspend fun doLogin(username: String, password: String): Customer

}