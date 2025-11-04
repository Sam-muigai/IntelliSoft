package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveVitalsData(
    @SerialName("id")
    val id: Int,
    @SerialName("message")
    val message: String,
    @SerialName("patient_id")
    val patientId: String,
    @SerialName("slug")
    val slug: Int,
)
