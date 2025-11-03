package com.samkt.intellisoft.data.di

import com.samkt.intellisoft.data.repositories.AuthRepositoryImpl
import com.samkt.intellisoft.domain.repositories.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}