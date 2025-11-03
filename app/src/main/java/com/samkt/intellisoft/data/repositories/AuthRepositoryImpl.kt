package com.samkt.intellisoft.data.repositories

import com.samkt.intellisoft.core.networking.IntellisoftApiService
import com.samkt.intellisoft.core.networking.helpers.ApiResponse
import com.samkt.intellisoft.data.mappers.toData
import com.samkt.intellisoft.domain.model.SignUp
import com.samkt.intellisoft.domain.repositories.AuthRepository
import com.samkt.intellisoft.domain.helpers.Result

class AuthRepositoryImpl(
    private val intellisoftApiService: IntellisoftApiService
) : AuthRepository {

    override suspend fun signUp(signUp: SignUp): Result<String> {
        return when (val response = intellisoftApiService.signUp(signUp.toData())) {
            is ApiResponse.Error -> Result.Error(response.errorMessage)
            is ApiResponse.Success -> Result.Success(response.data.signUpData.message)
        }
    }
}