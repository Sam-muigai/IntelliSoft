package com.samkt.intellisoft.core.networking.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddPatientRequest(
    @SerialName("dob")
    val dob: String,
    @SerialName("firstname")
    val firstname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("reg_date")
    val regDate: String,
    @SerialName("unique")
    val unique: String,
)
