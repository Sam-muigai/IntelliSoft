package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
)
