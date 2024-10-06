package com.example.infosysassignment.feature.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infosysassignment.feature.authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUIState(
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
)

class LoginViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    companion object {
        const val TAG = "RENTAL_VIEWMODEL"
    }

    private val _loginUIState: MutableStateFlow<LoginUIState> =
        MutableStateFlow(LoginUIState())
    val loginUIState = _loginUIState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loginUIState.value =
                    _loginUIState.value.copy(isLoading = true)
                val response = repository.doLogin(username, password)
                _loginUIState.value = _loginUIState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = true
                )
            } catch (e: Exception) {
                Log.e(TAG, "Problems getting max speed limit")
            }
        }
    }

    fun resetLoginStatus() {
        _loginUIState.value = _loginUIState.value.copy(isLoginSuccessful = false)
    }
}