package com.samkt.intellisoft.core.networking.di

import com.samkt.intellisoft.core.networking.IntellisoftApiService
import com.samkt.intellisoft.core.networking.IntellisoftApiServiceImpl
import com.samkt.intellisoft.core.networking.getKtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { getKtorClient() }
    single<IntellisoftApiService> { IntellisoftApiServiceImpl(get()) }
}