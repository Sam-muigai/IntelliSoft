package com.samkt.intellisoft.core.networking.helpers

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}


suspend inline fun <reified T> safeApiCall(
    apiCall: () -> HttpResponse
): ApiResponse<T> {
    return try {
        val response = apiCall()
        ApiResponse.Success(response.body())
    } catch (e: Exception) {
        ApiResponse.Error(e.message ?: "Unexpected error occurred")
    }
}