package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveVisitRequest(
    @SerialName("comments")
    val comments: String,
    @SerialName("general_health")
    val generalHealth: String,
    @SerialName("on_diet")
    val onDiet: String,
    @SerialName("on_drugs")
    val onDrugs: String,
    @SerialName("patient_id")
    val patientId: String,
    @SerialName("visit_date")
    val visitDate: String,
    @SerialName("vital_id")
    val vitalId: String,
)
