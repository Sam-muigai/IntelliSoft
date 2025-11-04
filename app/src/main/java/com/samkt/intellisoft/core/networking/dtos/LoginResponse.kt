package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val loginData: LoginData,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
)
