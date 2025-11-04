package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val signUpData: SignUpData,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
)
