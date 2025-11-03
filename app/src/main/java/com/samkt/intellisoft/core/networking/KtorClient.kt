package com.samkt.intellisoft.core.networking

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun getKtorClient() = HttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                coerceInputValues = true
                prettyPrint = true
                isLenient = true
            },
        )
    }

    install(Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 20_000
        connectTimeoutMillis = 20_000
        socketTimeoutMillis = 20_000
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, "application/json")
    }
}