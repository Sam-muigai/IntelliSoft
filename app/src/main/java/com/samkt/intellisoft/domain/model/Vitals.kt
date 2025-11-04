package com.samkt.intellisoft.domain.model

import java.time.LocalDate

data class Vitals(
    val height: String,
    val weight: String,
    val patientId: Int,
    val visitDate: LocalDate
)