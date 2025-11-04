package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveVitalsRequest(
    @SerialName("bmi")
    val bmi: String,
    @SerialName("height")
    val height: String,
    @SerialName("patient_id")
    val patientId: String,
    @SerialName("visit_date")
    val visitDate: String,
    @SerialName("weight")
    val weight: String,
)
