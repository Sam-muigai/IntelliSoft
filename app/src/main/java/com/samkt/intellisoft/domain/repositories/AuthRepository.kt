package com.samkt.intellisoft.domain.repositories

import com.samkt.intellisoft.domain.helpers.Result
import com.samkt.intellisoft.domain.model.Login
import com.samkt.intellisoft.domain.model.SignUp

interface AuthRepository {
    suspend fun signUp(signUp: SignUp): Result<String>

    suspend fun login(login: Login): Result<String>
}