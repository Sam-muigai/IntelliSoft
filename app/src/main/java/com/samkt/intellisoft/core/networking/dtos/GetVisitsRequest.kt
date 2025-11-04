package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVisitsRequest(
    @SerialName("visit_date")
    val visitDate: String,
)
