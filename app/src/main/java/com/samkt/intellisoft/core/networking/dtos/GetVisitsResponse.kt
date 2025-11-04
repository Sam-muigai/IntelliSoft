package com.samkt.intellisoft.core.networking.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVisitsResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("data")
    val visits: List<VisitData>,
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean
)