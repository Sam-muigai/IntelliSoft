package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisitData(
    @SerialName("age")
    val age: Int,
    @SerialName("bmi")
    val bmi: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String,
)
