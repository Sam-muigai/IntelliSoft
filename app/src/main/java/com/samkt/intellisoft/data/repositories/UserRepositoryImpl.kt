package com.samkt.intellisoft.data.repositories

import com.samkt.intellisoft.core.preferences.AppPreferences
import com.samkt.intellisoft.domain.model.User
import com.samkt.intellisoft.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val appPreferences: AppPreferences
) : UserRepository {
    override fun getUser(): Flow<User>{
        val fullNameFlow = appPreferences.getFullName()
        val emailFlow = appPreferences.getEmail()

        return fullNameFlow.combine(emailFlow) { fullName, email ->
            User(
                fullName = fullName,
                email = email
            )
        }
    }
}