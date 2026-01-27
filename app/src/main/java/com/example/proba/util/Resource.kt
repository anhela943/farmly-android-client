package com.example.proba.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val errors: List<String>? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
