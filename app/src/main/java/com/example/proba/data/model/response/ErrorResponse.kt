package com.example.proba.data.model.response
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val message: String,
    @SerializedName("errors")
    val errors: List<FieldError>? = null
)

data class FieldError(
    @SerializedName("field")
    val field: String,
    @SerializedName("message")
    val message: String
)

data class RegisterErrorResponse(
    @SerializedName("error")
    val error: List<String>
)
