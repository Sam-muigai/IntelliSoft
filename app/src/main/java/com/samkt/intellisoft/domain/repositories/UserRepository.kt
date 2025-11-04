package com.samkt.intellisoft.domain.repositories

import com.samkt.intellisoft.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): Flow<User>
}
