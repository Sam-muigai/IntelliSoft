package com.samkt.intellisoft.domain.model

import java.time.LocalDate

data class Assessment(
    val id: Int,
    val generalHealth: String,
    val onDiet: String,
    val onDrugs: String,
    val comments: String,
    val visitDate: LocalDate,
    val vitalId: Int,
    val patientBackendId: String = "",
    val vitalsBackendId: String = "",
)
