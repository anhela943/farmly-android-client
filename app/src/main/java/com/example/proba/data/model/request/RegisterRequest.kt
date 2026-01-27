package com.example.proba.data.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
