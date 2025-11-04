package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveVisitData(
    @SerialName("message")
    val message: String,
    @SerialName("slug")
    val slug: Int,
)
