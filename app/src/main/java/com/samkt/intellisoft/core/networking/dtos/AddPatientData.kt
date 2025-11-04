package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddPatientData(
    @SerialName("message")
    val message: String,
    @SerialName("proceed")
    val proceed: Int,
)
