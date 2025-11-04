package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatientsResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val `data`: List<PatientData>,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean,
)
