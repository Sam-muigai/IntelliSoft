package com.samkt.intellisoft.data.repositories

import com.samkt.intellisoft.core.networking.IntellisoftApiService
import com.samkt.intellisoft.core.networking.helpers.ApiResponse
import com.samkt.intellisoft.core.preferences.AppPreferences
import com.samkt.intellisoft.data.mappers.toData
import com.samkt.intellisoft.domain.helpers.Result
import com.samkt.intellisoft.domain.model.Login
import com.samkt.intellisoft.domain.model.SignUp
import com.samkt.intellisoft.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val intellisoftApiService: IntellisoftApiService,
    private val appPreferences: AppPreferences,
) : AuthRepository {

    override suspend fun signUp(signUp: SignUp): Result<String> {
        return when (val response = intellisoftApiService.signUp(signUp.toData())) {
            is ApiResponse.Error -> Result.Error(response.errorMessage)
            is ApiResponse.Success -> Result.Success(response.data.signUpData.message)
        }
    }

    override suspend fun login(login: Login): Result<String> {
        return when (val response = intellisoftApiService.login(login.toData())) {
            is ApiResponse.Error -> Result.Error(response.errorMessage)
            is ApiResponse.Success -> {
                // Store access token
                val loginData = response.data.loginData
                appPreferences.saveAccessToken(loginData.accessToken)
                appPreferences.saveFullName(loginData.name)
                appPreferences.saveEmail(loginData.email)
                Result.Success(response.data.message)
            }
        }
    }
}
