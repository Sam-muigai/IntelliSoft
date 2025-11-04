package com.samkt.intellisoft.data.di

import com.samkt.intellisoft.data.repositories.AuthRepositoryImpl
import com.samkt.intellisoft.data.repositories.PatientRepositoryImpl
import com.samkt.intellisoft.data.repositories.UserRepositoryImpl
import com.samkt.intellisoft.domain.repositories.AuthRepository
import com.samkt.intellisoft.domain.repositories.PatientRepository
import com.samkt.intellisoft.domain.repositories.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<PatientRepository> { PatientRepositoryImpl(get(),get()) }
}