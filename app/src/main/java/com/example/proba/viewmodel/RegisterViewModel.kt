package com.example.proba.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.repository.AuthRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val city: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterSuccessful: Boolean = false
)

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onFullNameChange(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phoneNumber)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun register() {
        val state = _uiState.value

        Log.d(TAG, "Register attempt started for email: ${state.email}")

        if (state.fullName.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            Log.e(TAG, "Register failed: Required fields are empty")
            _uiState.value = state.copy(
                errorMessage = "Full name, email and password are required"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            when (val result = authRepository.register(
                fullName = state.fullName,
                city = state.city,
                phoneNumber = state.phoneNumber,
                email = state.email,
                password = state.password
            )) {
                is Resource.Success -> {
                    Log.d(TAG, "Registration successful for email: ${state.email}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegisterSuccessful = true
                    )
                }
                is Resource.Error -> {
                    Log.e(TAG, "Registration failed for email: ${state.email} - ${result.message}")
                    result.errors?.forEach { error ->
                        Log.e(TAG, "Validation error: $error")
                    }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Registration in progress...")
                }
            }
        }
    }
}
