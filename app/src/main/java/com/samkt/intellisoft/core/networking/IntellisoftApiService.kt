package com.samkt.intellisoft.core.networking

import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.core.networking.dtos.SignUpResponse
import com.samkt.intellisoft.core.networking.helpers.ApiResponse

interface IntellisoftApiService {
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<SignUpResponse>
}