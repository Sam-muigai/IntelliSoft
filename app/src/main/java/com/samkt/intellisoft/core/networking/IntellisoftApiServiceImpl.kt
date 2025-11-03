package com.samkt.intellisoft.core.networking

import com.samkt.intellisoft.core.networking.dtos.LoginRequest
import com.samkt.intellisoft.core.networking.dtos.LoginResponse
import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.core.networking.dtos.SignUpResponse
import com.samkt.intellisoft.core.networking.helpers.ApiResponse
import com.samkt.intellisoft.core.networking.helpers.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class IntellisoftApiServiceImpl(
    private val client: HttpClient
) : IntellisoftApiService {
    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<SignUpResponse> {
        return safeApiCall {
            client.post(BASE_URL + "user/signup") {
                setBody(signUpRequest)
            }
        }
    }

    override suspend fun login(loginRequest: LoginRequest): ApiResponse<LoginResponse> {
        return safeApiCall {
            client.post(BASE_URL + "user/signin") {
                setBody(loginRequest)
            }
        }
    }

    companion object {
        const val BASE_URL = "https://patientvisitapis.intellisoftkenya.com/api/"
    }
}